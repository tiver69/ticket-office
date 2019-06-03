package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;

import java.util.List;

public class TicketService {

    private static Logger LOG = Logger.getLogger(TicketService.class);
    private TrainCoachService trainCoachService = new TrainCoachService();
    private TrainStationService trainStationService = new TrainStationService();

    public int countTicketPrice(Ticket ticket) {
        List<TrainStation> trainStationRoot = trainStationService.getTrainStationRoot(
                ticket.getDepartureStation().getId(), ticket.getDestinationStation().getId(),
                ticket.getTrainCoach().getTrain().getId());
        int rootPrise = trainStationRoot.stream()
                .mapToInt(TrainStation::getPrise).sum();
        int markupForTrain = ticket.getTrainCoach().getTrain().getMarkup() * rootPrise / 100;
        int markupForCoachType = ticket.getTrainCoach().getCoachType().getMarkup() * rootPrise / 100;
        LOG.debug(String.format("Count prise for ticket is %d + %d + %d"
                , rootPrise, markupForCoachType, markupForTrain));

        rootPrise = rootPrise + markupForCoachType + markupForTrain;
        LOG.info("Count prise for ticket is " + rootPrise);
        return rootPrise;
    }

    public void fillTicket(Ticket ticket) {

        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()) {
            trainCoachDao.getById(ticket.getTrainCoach().getId())
                    .ifPresent(ticket::setTrainCoach);
            trainCoachService.fillTrainCoachType(ticket.getTrainCoach());
            trainCoachService.fillTrainCoachTrain(ticket.getTrainCoach());
        }
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationDao.getById(ticket.getDepartureStation().getId())
                    .ifPresent(ticket::setDepartureStation);
            stationDao.getById(ticket.getDestinationStation().getId())
                    .ifPresent(ticket::setDestinationStation);
        }
    }

    public boolean checkIfTicketAvailableForRoot(int departureStationId, int destinationStationId, Ticket ticket) {
        fillTicket(ticket);
        int trainId = ticket.getTrainCoach().getTrain().getId();
        int ticketDepartureOrder = trainStationService.getTrainStationOrder(ticket.getDepartureStation().getId(), trainId);
        int ticketDestinationOrder = trainStationService.getTrainStationOrder(ticket.getDestinationStation().getId(), trainId);
        int requestDepartureOrder = trainStationService.getTrainStationOrder(departureStationId, trainId);
        int requestDestinationOrder = trainStationService.getTrainStationOrder(destinationStationId, trainId);
        LOG.debug(String.format("Search for ticket formula: (%d-%d)*(%d-%d)>0",requestDepartureOrder,ticketDestinationOrder,
                requestDestinationOrder,ticketDepartureOrder));

        return ((requestDestinationOrder - ticketDepartureOrder)
                * (requestDepartureOrder - ticketDestinationOrder) >= 0);
    }
}

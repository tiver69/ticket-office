package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.service.utils.StationService;

import java.util.List;

public class TicketService {

    private static Logger LOG = Logger.getLogger(TicketService.class);
    private TrainCoachService trainCoachService = new TrainCoachService();
    private StationService stationService = new StationService();
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
        ticket.setTrainCoach(trainCoachService.getTrainCoach(ticket.getTrainCoach().getId()));
        ticket.setDepartureStation(stationService.getStation(ticket.getDepartureStation().getId()));
        ticket.setDestinationStation(stationService.getStation(ticket.getDestinationStation().getId()));
    }

    public boolean checkIfPlaceUnavailableForTicket(Ticket ticket) {
        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            return ticketDao.getTicketsByCoachIdAndDate(ticket.getTrainCoach().getId(), ticket.getDate())
                    .stream().filter(foundTicket ->
                            foundTicket.getPlace() == ticket.getPlace())
                    .anyMatch(foundTicket ->
                            !checkIfTicketAvailableForRoot(
                                    ticket.getDepartureStation().getId(), ticket.getDestinationStation().getId(),
                                    foundTicket));
        }
    }

    public boolean checkIfTicketAvailableForRoot(int departureStationId, int destinationStationId, Ticket ticket) {
        fillTicket(ticket);
        int trainId = ticket.getTrainCoach().getTrain().getId();
        int ticketDepartureOrder = trainStationService.getTrainStationOrder(ticket.getDepartureStation().getId(), trainId);
        int ticketDestinationOrder = trainStationService.getTrainStationOrder(ticket.getDestinationStation().getId(), trainId);
        int requestDepartureOrder = trainStationService.getTrainStationOrder(departureStationId, trainId);
        int requestDestinationOrder = trainStationService.getTrainStationOrder(destinationStationId, trainId);
        LOG.debug(String.format("Search for ticket formula: (%d-%d)*(%d-%d)>0", requestDepartureOrder, ticketDestinationOrder,
                requestDestinationOrder, ticketDepartureOrder));

        return ((requestDestinationOrder - ticketDepartureOrder)
                * (requestDepartureOrder - ticketDestinationOrder) >= 0);
    }
}

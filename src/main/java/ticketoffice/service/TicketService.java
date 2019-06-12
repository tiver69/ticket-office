package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import java.util.List;

public class TicketService {

    private static Logger LOG = Logger.getLogger(TicketService.class);
    private TrainCoachService trainCoachService = new TrainCoachService();
    private StationService stationService = new StationService();
    private TrainStationService trainStationService = new TrainStationService();

    /**
     * @param ticket
     * @return Count total price for requested ticket using formula:
     * prise = sum(trainStation.price) + sum(trainStation.price) * CoachType.markUp% +
     * sum(trainStation.price) * Train.markUp%.
     */
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

    /**
     * Get all additional information for Ticket fields and set extracted values to
     * request ticket parameter.
     */
    public void fillTicket(Ticket ticket, String locale) {
        ticket.setTrainCoach(trainCoachService.getTrainCoach(ticket.getTrainCoach().getId(), locale));
        ticket.setDepartureStation(stationService.getStation(ticket.getDepartureStation().getId(), locale));
        ticket.setDestinationStation(stationService.getStation(ticket.getDestinationStation().getId(), locale));
    }

    /**
     * Check if any ticket for current coach and date(from parameter ticket) is occupied place
     * for request root (from ticket.departureStation to ticket.destinationStation)
     * using checkIfTicketAvailableForRoot method.
     *
     * @param ticket
     * @return "true" if place if occupied, "false" if place if available.
     */
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

    /**
     * Check if existing ticket(parameter ticket) isn't occupied place for request root (from departureStation to
     * destinationStation) using math formula for checking if segments intersect.
     * For segment1 = x1y1, and segment2 = x2,y2, they will intersect in case (y2 - x1)*(x2 - y1) < 0.
     * Using train station order as values for x1, y1, x2 and y2.
     *
     * @return boolean "true" if place if available, "false" if occupied.
     */
    public boolean checkIfTicketAvailableForRoot(int departureStationId, int destinationStationId, Ticket ticket) {
        fillTicket(ticket, "ru");
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

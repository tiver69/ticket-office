package ticketoffice.facade;

import org.apache.log4j.Logger;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.model.Ticket;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Service class for creating TicketInfoDto objects
 */
public class TicketInfoFacade {

    private static Logger LOG = Logger.getLogger(TicketInfoFacade.class);
    private TrainStationService trainStationService = new TrainStationService();

    /**
     * Load requested ticket information form bd, counts full date-time
     * values of arrival&departure
     *
     * @param ticket -   ticket item from request, filled with basic info (id-fields).
     * @return TicketInfoDto value
     */
    public TicketInfoDto loadTicketInfo(Ticket ticket) {
        int trainId = ticket.getTrainCoach().getTrain().getId();
        Time depatrureTime = trainStationService.getTrainStation(
                ticket.getDepartureStation().getId(), trainId).getDepartureTime();
        Time arrivalTime = trainStationService.getTrainStation(
                ticket.getDestinationStation().getId(), trainId).getArrivalTime();
        Time duration = TimeDateUtil.getTimeDiff(depatrureTime, arrivalTime);

        Timestamp departureDateTime = new Timestamp(ticket.getDate().getTime()
                + TimeDateUtil.convertToLocalTime(depatrureTime).getTime());
        Timestamp arrivalDateTime = new Timestamp(ticket.getDate().getTime()
                + TimeDateUtil.convertToLocalTime(depatrureTime).getTime()
                + TimeDateUtil.convertToLocalTime(duration).getTime());

        TicketInfoDto ticketInfoDto = new TicketInfoDto();
        ticketInfoDto.setTicket(ticket);
        ticketInfoDto.setDepartureDateTime(departureDateTime);
        ticketInfoDto.setArrivalDateTime(arrivalDateTime);

        LOG.debug(String.format("Get depatrure time %s and arrival time %s for ticket",
                departureDateTime.toString(), arrivalDateTime.toString()));
        return ticketInfoDto;
    }
}

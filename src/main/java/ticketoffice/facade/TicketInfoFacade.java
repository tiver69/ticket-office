package ticketoffice.facade;

import org.apache.log4j.Logger;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.model.Ticket;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Time;
import java.sql.Timestamp;

public class TicketInfoFacade {

    private static Logger LOG = Logger.getLogger(TicketInfoFacade.class);
    private TrainStationService trainStationService = new TrainStationService();

    public TicketInfoDto loadTicketInfo(Ticket ticket) {
        int trainId = ticket.getTrainCoach().getTrain().getId();
        Time depatrureTime = trainStationService.getDepartureTime(
                ticket.getDepartureStation().getId(), trainId);
        Time arrivalTime = trainStationService.getArrivalTime(
                ticket.getDestinationStation().getId(), trainId);
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

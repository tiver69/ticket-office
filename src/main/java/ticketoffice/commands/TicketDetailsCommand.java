package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.facade.TicketInfoFacade;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.mapper.TicketMapper;
import ticketoffice.service.TicketService;
import ticketoffice.validator.DateValidator;

import javax.servlet.http.HttpServletRequest;

public class TicketDetailsCommand implements Command {

    private static Logger LOG = Logger.getLogger(TicketDetailsCommand.class);
    private TicketService ticketService = new TicketService();
    private TicketMapper ticketMapper = new TicketMapper();
    private TicketInfoFacade ticketInfoFacade = new TicketInfoFacade();
    private DateValidator dateValidator = new DateValidator();

    @Override
    public String execute(HttpServletRequest request) {

        try {
            Ticket ticket = ticketMapper.extractItemFromRequest(request);
            ticketService.fillTicket(ticket);
            validateRequest(ticket);
            ticket.setPrice(ticketService.countTicketPrice(ticket));
            LOG.info("Ticket request: " + ticket.toString());

            TicketInfoDto ticketInfoDto = ticketInfoFacade.loadTicketInfo(ticket);
            request.setAttribute("ticket", ticketInfoDto);
        } catch (ValidateFailException e) {
            request.setAttribute("errorCode", e.getErrorKeyMessage());
            return ("error/400");
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments in search parameters for /ticketDetail request");
            return ("error/400");
        }
        return "user/booking/ticket";
    }

    private void validateRequest(Ticket ticket) throws ValidateFailException {
        dateValidator.validatePastDate(ticket.getDate());
        if (ticket.getPlace() <= 0 ||
                ticket.getPlace() > ticket.getTrainCoach().getCoachType().getPlaces() ||
                ticketService.checkIfPlaceUnavailableForTicket(ticket)) {
            LOG.error(String.format("Place #%d doesn't exist in coach #%d",
                    ticket.getPlace(), ticket.getTrainCoach().getCoachType().getId()));
            throw new ValidateFailException("place");
        }
    }
}

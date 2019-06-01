package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.facade.TicketInfoFacade;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.mapper.TicketMapper;
import ticketoffice.service.TicketService;

import javax.servlet.http.HttpServletRequest;

public class TicketDetailsCommand implements Command {

    private static Logger LOG = Logger.getLogger(TicketDetailsCommand.class);
    private TicketService ticketService = new TicketService();
    private TicketMapper ticketMapper = new TicketMapper();
    private TicketInfoFacade ticketInfoFacade = new TicketInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {

        Ticket ticket = ticketMapper.extractItemFromRequest(request);
        ticketService.fillTicket(ticket);
        ticket.setPrice(ticketService.countTicketPrice(ticket));
        LOG.info("Ticket request: " + ticket.toString());

        TicketInfoDto ticketInfoDto = ticketInfoFacade.loadTicketInfo(ticket);
        request.setAttribute("ticket", ticketInfoDto);

        return "user/booking/ticket";
    }
}

package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.mapper.TicketMapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Get all tickets values from request, creates single ticket value, creates new
 * ticket record.
 * Redirects to /user/home page if there was no errors.
 */
public class SaveTicketCommand implements Command {

    private static Logger LOG = Logger.getLogger(SaveTicketCommand.class);
    private TicketMapper ticketMapper = new TicketMapper();

    @Override
    public String execute(HttpServletRequest request) {
        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            Ticket ticket = ticketMapper.extractItemFromRequest(request);
            ticket.setPrice(Integer.parseInt(request.getParameter("price")));

            LOG.info("Book ticket " + ticket + " for passenger");
            ticketDao.create(ticket);
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments for /saveTicket request");
            return ("error/400");
        }

        return "redirect/user/home";
    }
}

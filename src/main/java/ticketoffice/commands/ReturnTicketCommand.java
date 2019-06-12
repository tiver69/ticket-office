package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import javax.servlet.http.HttpServletRequest;

/**
 * Get ID of ticket for returning, persist request for remove ticket.
 * Redirect to user/home page to load new settings if there was no errors.
 */
public class ReturnTicketCommand implements Command {

    private static Logger LOG = Logger.getLogger(ReturnTicketCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            LOG.info("Request for returning ticket#" + ticketId);
            ticketDao.delete(ticketId);
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments for /returnTicket request");
            return ("error/400");
        }

        return "redirect/user/home";
    }
}

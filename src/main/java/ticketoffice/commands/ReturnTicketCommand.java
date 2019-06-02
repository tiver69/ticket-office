package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import javax.servlet.http.HttpServletRequest;

public class ReturnTicketCommand implements Command {

    private static Logger LOG = Logger.getLogger(ReturnTicketCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        LOG.info("Request for returning ticket#" + ticketId);

        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()){
            ticketDao.delete(ticketId);
        }
        return "redirect/user/home";
    }
}

package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.mapper.TicketMapper;

import javax.servlet.http.HttpServletRequest;

public class SaveTicketCommand implements Command {

    private static Logger LOG = Logger.getLogger(SaveTicketCommand.class);
    private TicketMapper ticketMapper = new TicketMapper();

    @Override
    public String execute(HttpServletRequest request) {

        Ticket ticket = ticketMapper.extractItemFromRequest(request);
        ticket.setPrice(Integer.parseInt(request.getParameter("price")));

        LOG.info("Book ticket " + ticket + " for passenger");

        try(TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()){
            ticketDao.create(ticket);
        }

        return "redirect/user/home";
    }
}

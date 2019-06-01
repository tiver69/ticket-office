package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Ticket;

import java.sql.Date;
import java.util.List;

public interface TicketDao extends AbstractDao<Ticket> {

    List<Ticket> getTicketsByCoachIdAndDate(int coachId, Date date);

    List<Ticket> getTicketsByPassengerId(int passengerId);

}

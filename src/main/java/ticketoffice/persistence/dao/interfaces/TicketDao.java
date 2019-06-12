package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Interface for sql-queries in Ticket table.
 */
public interface TicketDao extends AbstractDao<Ticket> {

    /**
     * Extract List of items from db related to Coach with specified ID.
     * and requested date.
     *
     * @param coachId -   int variable of requested coach ID,
     * @param date    -   Date variable of requested date,
     * @return List of Tickets.
     */
    List<Ticket> getTicketsByCoachIdAndDate(int coachId, Date date);

    /**
     * Extract List of items from db related to Passenger with specified ID.
     *
     * @param passengerId -   int variable of requested passenger ID,
     * @return List of Tickets.
     */
    List<Ticket> getTicketsByPassengerId(int passengerId);

}

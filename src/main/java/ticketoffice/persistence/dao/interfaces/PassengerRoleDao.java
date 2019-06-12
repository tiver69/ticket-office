package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.PassengerRole;

import java.util.List;

/**
 * Interface for sql-queries in PassengerRole table.
 */
public interface PassengerRoleDao extends AbstractDao<PassengerRole> {


    /**
     * Extract List of items from db related to Passenger with specified ID
     *
     * @param passengerId -   int variable of requested passenger ID
     * @return List of PassengerRole
     */
    List<PassengerRole> getPassengerRolesByPassengerId(int passengerId);
}

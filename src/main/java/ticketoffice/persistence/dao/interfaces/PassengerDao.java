package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Passenger;

import java.util.Optional;

/**
 * Interface for sql-queries in Passenger table.
 */
public interface PassengerDao extends AbstractDao<Passenger> {

    /**
     * Extract single item from db according to given login-value
     *
     * @param login -   string variable with requested login
     * @return Optional of Passenger if value was successfully extracted, Optional of Nullable
     * if there was no record in db
     */
    Optional<Passenger> getByLogin(String login);


    /**
     * Insert Passenger and PassengerRole(always - USER) items to db in one transaction.
     *
     * @param passenger -   Passenger value for insert,
     * @return -   return 1 if both items was successfully insert in tables, return 0 - if insert fail.
     */
    int createUser(Passenger passenger);
}

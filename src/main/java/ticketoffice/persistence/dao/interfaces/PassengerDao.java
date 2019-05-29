package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Passenger;

import java.util.Optional;

public interface PassengerDao extends AbstractDao<Passenger> {

    Optional<Passenger> getByLogin(String login);

    int createUser(Passenger passenger);
}

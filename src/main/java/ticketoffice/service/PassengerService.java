package ticketoffice.service;

import org.apache.commons.codec.digest.DigestUtils;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import java.util.Optional;

public class PassengerService {

    public boolean registerPassenger(Passenger passenger) {
        try (PassengerDao passengerDaoImpl = DaoFactory.getInstance().getPassengerDao()) {
            return passengerDaoImpl.createUser(passenger) == 1;
        }
    }

    public Optional<Passenger> loginPassenger(String login, String password) {
        try (PassengerDao passengerDaoImpl = DaoFactory.getInstance().getPassengerDao()) {
            Optional<Passenger> passenger = passengerDaoImpl.getByLogin(login);
            String passwordHash = DigestUtils.md5Hex(password);
            if (passenger.isPresent() &&
                    passenger.get().getPassword().equals(passwordHash)) {
                passenger.get().setPassword("");
                return passenger;
            }
        }
        return Optional.empty();
    }
}

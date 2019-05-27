package ticketoffice.service;

import org.apache.commons.codec.digest.DigestUtils;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

public class PassengerService {

    public boolean registerPassenger(Passenger passenger) {
        try (PassengerDao passengerDaoImpl = DaoFactory.getInstance().getPassengerDao()) {
            return passengerDaoImpl.createUser(passenger) == 1;
        }
    }

    public Passenger loginPassenger(String login, String password) {
        try (PassengerDao passengerDaoImpl = DaoFactory.getInstance().getPassengerDao()) {
            Passenger passenger = passengerDaoImpl.getByLogin(login);
            String passwordHash = DigestUtils.md5Hex(password);
            if (passenger.getPassword().equals(passwordHash)) {
                passenger.setPassword("");
                return passenger;
            }
        }
        return null;
    }
}

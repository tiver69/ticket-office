package ticketoffice.service;

import org.apache.commons.codec.digest.DigestUtils;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

public class PassengerService {

    PassengerDao passengerDaoImpl;

    public PassengerService() {
        this.passengerDaoImpl = DaoFactory.getInstance().getPassengerDao();
    }

    public boolean registerPassenger(Passenger passenger) {
        return passengerDaoImpl.create(passenger) == 1;
    }

    public int loginPassenger(String login, String password) {
        Passenger passenger = passengerDaoImpl.getByLogin(login);
        String passwordHash = DigestUtils.md5Hex(password);
        if (passenger.getPassword().equals(passwordHash))
            return passenger.getId();
        return 0;

    }
}

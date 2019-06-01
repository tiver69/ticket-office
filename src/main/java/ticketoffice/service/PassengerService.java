package ticketoffice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import java.util.Optional;

public class PassengerService {

    private static Logger LOG = Logger.getLogger(PassengerService.class);

    public boolean registerPassenger(Passenger passenger) {
        try (PassengerDao passengerDaoImpl = DaoFactory.getInstance().getPassengerDao()) {
            boolean status = passengerDaoImpl.createUser(passenger) == 1;
            if (status)
                LOG.info("New passenger " + passenger.getLogin() + " successfully register.");
            else
                LOG.error("Passenger " + passenger.getLogin() + " registration fail.");
            return status;
        }
    }

    public Optional<Passenger> loginPassenger(String login, String password) {
        try (PassengerDao passengerDaoImpl = DaoFactory.getInstance().getPassengerDao()) {
            Optional<Passenger> passenger = passengerDaoImpl.getByLogin(login);
            String passwordHash = DigestUtils.md5Hex(password);
            if (passenger.isPresent() &&
                    passenger.get().getPassword().equals(passwordHash)) {
                passenger.get().setPassword("");
                LOG.info("Successful authentication from " + login);
                return passenger;
            }
        }
        LOG.info("Fail to authenticate " + login);
        return Optional.empty();
    }
}

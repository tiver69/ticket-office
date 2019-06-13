package ticketoffice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import java.util.Optional;

public class PassengerService {

    private static Logger LOG = Logger.getLogger(PassengerService.class);

    /**
     * @param passengerId
     * @return extracted Passenger from Optional value.
     * @throws ValidateFailException in case no item was not found.
     */
    public Passenger getPassenger(int passengerId) {
        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            Optional<Passenger> passenger = passengerDao.getById(passengerId);
            if (passenger.isPresent())
                return passenger.get();
        }

        LOG.error(String.format("Passenger with id#%d doesn't exist", passengerId));
        throw new ValidateFailException("passenger");
    }


    /**
     * Persist passenger value in Dao for creating new items
     *
     * @param passenger
     * @return 1 in case successful registration, 0 - in case of fail.
     */
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

    /**
     * Extracted Passenger item by request login, compeer passwords' hash.
     *
     * @param login
     * @param password
     * @return extracted Passenger, with password field set to "" from Optional value
     * if password matches, Optional of Nullable in case comparing fail or no value was
     * extracted from db
     */
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

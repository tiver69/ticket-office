package ticketoffice.validator;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import java.util.Optional;

public class PassengerValidator {

    private static Logger LOG = Logger.getLogger(PassengerValidator.class);

    public void validatePassengerInfo(String firstName, String lastName, String login) {
        String loginPattern = "[a-z0-9_-]{3,16}";
        String namePattern = "[A-ZА-Я][A-Za-zА-Яа-я-]*";

        if (!login.matches(loginPattern)
                || !firstName.matches(namePattern)
                || !lastName.matches(namePattern)) {
            LOG.error(String.format("Data %s(%b) %s(%b) %s(%b) doesn't match pattern for user",
                    lastName, lastName.matches(namePattern),
                    firstName, firstName.matches(namePattern),
                    login, login.matches(loginPattern)));
            throw new ValidateFailException("passenger.info");
        }
    }

    public void validateNewPassengerLogin(String login) {
        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            Optional<Passenger> passenger = passengerDao.getByLogin(login);
            if (passenger.isPresent()) {
                LOG.error(String.format("Passenger with login %s already exist", login));
                throw new ValidateFailException("passenger.exist");
            }
        }
    }

    public void validateNewPassengerInfo(String firstName, String lastName, String login) {
        validatePassengerInfo(firstName, lastName, login);
        validateNewPassengerLogin(login);
    }
}

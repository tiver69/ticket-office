package ticketoffice.validator;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import java.util.Optional;

/**
 * Class for validate requested passenger string values before passing to db.
 */
public class PassengerValidator {

    private static Logger LOG = Logger.getLogger(PassengerValidator.class);

    /**
     * Validate names variables as its value must start from upper case and
     * contains only alphabet symbols and '-'.
     * Validate login variable as its value must be in size between 3-16 symbols and contains
     * latin symbols, numbers, '_' and '-'.
     *
     * @param firstName
     * @param lastName
     * @param login
     */
    public void validatePassengerInfo(String firstName, String lastName, String login) {
        String loginPattern = "[A-Za-z0-9_-]{3,16}";
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

    /**
     * Validate login for creating new or updating existing user as its new login must be unique.
     *
     * @param login
     */
    public void validateNewPassengerLogin(String login) {
        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            Optional<Passenger> passenger = passengerDao.getByLogin(login);
            if (passenger.isPresent()) {
                LOG.error(String.format("Passenger with login %s already exist", login));
                throw new ValidateFailException("passenger.exist");
            }
        }
    }

    /**
     * Validate both cases for creating new or updating existing user.
     */
    public void validateNewPassengerInfo(String firstName, String lastName, String login) {
        validatePassengerInfo(firstName, lastName, login);
        validateNewPassengerLogin(login);
    }
}

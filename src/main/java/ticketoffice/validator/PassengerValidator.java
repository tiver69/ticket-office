package ticketoffice.validator;

import org.apache.log4j.Logger;
import ticketoffice.commands.FindTrainCommand;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.service.PassengerService;

import java.util.Optional;

public class PassengerValidator {

    private static Logger LOG = Logger.getLogger(FindTrainCommand.class);

    public void validatePassengerInfo(String firstName, String lastName, String login){
        String loginPattern = "[a-z0-9_-]{3,16}";
        String namePattern = "[A-Z][A-Za-z-]*";

        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            Optional<Passenger> passenger = passengerDao.getByLogin(login);
            if (passenger.isPresent()) {
                LOG.error(String.format("Passenger with login %s already exist", login));
                throw new ValidateFailException("passenger.exist");
            }
        }

        if (!login.matches(loginPattern)
                || !firstName.matches(namePattern)
                || !lastName.matches(namePattern)) {
            LOG.error(String.format("Data %s %s %s doesn't match pattern for user",
                    lastName, firstName, login));
            throw new ValidateFailException("passenger.info");
        }
    }
}
package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.mapper.PassengerMapper;
import ticketoffice.service.PassengerService;
import ticketoffice.validator.PassengerValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * Get all Passengers attributes from request, check if they are valid, send create request to db.
 * Redirects to /login if there was no errors.
 * Redirects to /registration page if something went wrong.
 * Forward to error page if validation fails.
 */
public class SignUpCommand implements Command {

    private static Logger LOG = Logger.getLogger(SignUpCommand.class);
    private PassengerMapper passengerMapper = new PassengerMapper();
    private PassengerService passengerService = new PassengerService();
    private PassengerValidator passengerValidator = new PassengerValidator();

    @Override
    public String execute(HttpServletRequest request) {

        try {
            Passenger passenger = passengerMapper.extractItemFromRequest(request);
            LOG.info(String.format("Get sign up request: (%s, %s, %s)",
                    passenger.getFirstName(),
                    passenger.getLastName(),
                    passenger.getLogin()));
            passengerValidator.validateNewPassengerInfo(passenger.getFirstName(),
                    passenger.getLastName(), passenger.getLogin());

            if (passengerService.registerPassenger(passenger))
                return "redirect/login";
            else return "redirect/registration";

        } catch (ValidateFailException e) {
            request.setAttribute("errorCode", e.getErrorKeyMessage());
            return ("error/400");
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments for /updatePassenger request");
            return ("error/400");
        }
    }

}

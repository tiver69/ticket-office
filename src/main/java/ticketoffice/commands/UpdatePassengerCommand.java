package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.service.PassengerService;
import ticketoffice.validator.PassengerValidator;

import javax.servlet.http.HttpServletRequest;

public class UpdatePassengerCommand implements Command {

    private static Logger LOG = Logger.getLogger(PromoteToAdminCommand.class);
    private PassengerService passengerService = new PassengerService();
    private PassengerValidator passengerValidator = new PassengerValidator();

    @Override
    public String execute(HttpServletRequest request) {

        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            int passengerId = Integer.parseInt(request.getParameter("passengerId"));
            String firstName = request.getParameter("passengerFirstName");
            String lastName = request.getParameter("passengerLastName");
            String login = request.getParameter("passengerLogin");
            LOG.info(String.format("Request for updating passenger#%d", passengerId));
            passengerValidator.validatePassengerInfo(firstName, lastName, login);

            Passenger passenger = passengerService.getPassenger(passengerId);
            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setLogin(login);

            request.setAttribute("updateSuccess", passengerDao.update(passenger));
        } catch (ValidateFailException e) {
            request.setAttribute("errorCode", e.getErrorKeyMessage());
            return ("error/400");
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments for /updatePassenger request");
            return ("error/400");
        }

        return "redirect/admin/users";
    }
}

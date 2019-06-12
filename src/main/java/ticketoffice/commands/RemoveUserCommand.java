package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import javax.servlet.http.HttpServletRequest;

/**
 * Get ID of passenger for removing, persist request for remove user and all related data.
 * Redirect to admin/users page to load new settings.
 */
public class RemoveUserCommand implements Command {

    private static Logger LOG = Logger.getLogger(RemoveUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            int passengerId = Integer.parseInt(request.getParameter("passengerId"));
            LOG.info(String.format("Request for removing user#%d", passengerId));
            passengerDao.delete(passengerId);
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments for /removePassenger request");
            return ("error/400");
        }

        return "redirect/admin/users";
    }
}

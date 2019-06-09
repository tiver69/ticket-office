package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;
import ticketoffice.service.PassengerService;

import javax.servlet.http.HttpServletRequest;

public class PromoteToAdminCommand implements Command {

    private static Logger LOG = Logger.getLogger(PromoteToAdminCommand.class);
    private PassengerService passengerService = new PassengerService();

    @Override
    public String execute(HttpServletRequest request) {
        try (PassengerRoleDao passengerRoleDao = DaoFactory.getInstance().getPassengerRoleDao()) {
            int passengerId = Integer.parseInt(request.getParameter("passengerId"));
            Passenger passenger = passengerService.getPassenger(passengerId);
            passenger.setId(passengerId);

            PassengerRole passengerRole = new PassengerRole();
            passengerRole.setPassenger(passenger);
            passengerRole.setRole(Role.ADMIN);
            LOG.info(String.format("Request for promoting user#%d to ADMIN", passengerId));

            passengerRoleDao.create(passengerRole);
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments for /promoteToAdmin request");
            return ("error/400");
        }

        return "redirect/admin/users";
    }
}

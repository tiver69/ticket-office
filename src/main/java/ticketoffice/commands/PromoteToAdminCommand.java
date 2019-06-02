package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.facade.PassengerInfoFacade;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import javax.servlet.http.HttpServletRequest;

public class PromoteToAdminCommand implements Command {

    private static Logger LOG = Logger.getLogger(PromoteToAdminCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        int passengerId = Integer.parseInt(request.getParameter("passengerId"));
        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        PassengerRole passengerRole = new PassengerRole();
        passengerRole.setPassenger(passenger);
        passengerRole.setRole(Role.ADMIN);

        LOG.info(String.format("Request for promoting user#%d to ADMIN", passengerId));
        try(PassengerRoleDao passengerRoleDao = DaoFactory.getInstance().getPassengerRoleDao()){
            passengerRoleDao.create(passengerRole);
        }

        return "redirect/admin/users";
    }
}

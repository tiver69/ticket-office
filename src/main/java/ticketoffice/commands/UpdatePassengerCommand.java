package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.service.PassengerService;

import javax.servlet.http.HttpServletRequest;

public class UpdatePassengerCommand implements Command {

    private static Logger LOG = Logger.getLogger(PromoteToAdminCommand.class);
    private PassengerService passengerService = new PassengerService();

    @Override
    public String execute(HttpServletRequest request) {
        int passengerId = Integer.parseInt(request.getParameter("passengerId"));

        Passenger passenger = passengerService.loadPassenger(passengerId);
        passenger.setFirstName(request.getParameter("passengerFirstName"));
        passenger.setLastName(request.getParameter("passengerLastName"));
        passenger.setLogin(request.getParameter("passengerLogin"));

        LOG.info(String.format("Request for updating passenger#%d", passengerId));
        try(PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()){
            request.setAttribute("updateStatus", passengerDao.update(passenger));
        }

        return "redirect/admin/users";
    }
}

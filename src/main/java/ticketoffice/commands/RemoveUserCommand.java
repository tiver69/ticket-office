package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import javax.servlet.http.HttpServletRequest;

public class RemoveUserCommand  implements Command {

    private static Logger LOG = Logger.getLogger(RemoveUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        int passengerId = Integer.parseInt(request.getParameter("passengerId"));
        LOG.info(String.format("Request for removing user#%d", passengerId));

        try(PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()){
            passengerDao.delete(passengerId);
        }

        return "redirect/admin/users";
    }
}

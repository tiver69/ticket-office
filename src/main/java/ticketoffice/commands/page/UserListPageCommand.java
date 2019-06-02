package ticketoffice.commands.page;

import ticketoffice.commands.Command;
import ticketoffice.dto.PassengerInfoDto;
import ticketoffice.facade.PassengerInfoFacade;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserListPageCommand implements Command {

    PassengerInfoFacade passengerInfoFacade = new PassengerInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {

        List<PassengerInfoDto> passengerList = new ArrayList<>();

        try(PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()){
            passengerDao.getAll().forEach(passenger -> {
                passengerList.add(passengerInfoFacade.loadPassengerInfo(passenger));
            });
        }
        request.setAttribute("passengerList", passengerList);
        return "admin/userlist";
    }
}

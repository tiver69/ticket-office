package ticketoffice.commands.page;

import ticketoffice.commands.Command;
import ticketoffice.dto.PassengerInfoDto;
import ticketoffice.facade.PassengerInfoFacade;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserListPageCommand implements Command {

    PassengerInfoFacade passengerInfoFacade = new PassengerInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {

        List<PassengerInfoDto> passengerInfoList = new ArrayList<>();
        int currentPage = (int) request.getAttribute("currentPage") - 1;

        try(PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()){
            List<Passenger> passengerList = passengerDao.getAll();
            request.setAttribute("pages", (int) Math.ceil(((double) passengerList.size()) / 5));
            passengerList.stream()
                    .skip(currentPage * 5)
                    .limit(5)
                    .forEach(passenger -> {
                passengerInfoList.add(passengerInfoFacade.loadPassengerInfo(passenger));
            });
        }
        request.setAttribute("passengerList", passengerInfoList);
        return "admin/userlist";
    }
}

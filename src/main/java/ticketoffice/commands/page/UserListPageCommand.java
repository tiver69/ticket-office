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

/**
 * Set request attribute "pages" with total number of pages available.
 * Set request attribute "currentPage" with current number of page from request parameter.
 * Get list of users (from current page*5 to currentPage*5+5) item and set result list
 * to request attribute "passengerList".
 * Forward to admin/userlist page if there was no errors.
 */
public class UserListPageCommand implements Command {

    private PassengerInfoFacade passengerInfoFacade = new PassengerInfoFacade();
    private List<Passenger> passengerList;

    @Override
    public String execute(HttpServletRequest request) {

        List<PassengerInfoDto> passengerInfoList = new ArrayList<>();
        int currentPage = (int) request.getAttribute("currentPage") - 1;

        try (PassengerDao passengerDao = DaoFactory.getInstance().getPassengerDao()) {
            passengerList = passengerDao.getAll();
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

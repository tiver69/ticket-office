package ticketoffice.commands.page;

import ticketoffice.commands.Command;
import ticketoffice.model.Station;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        List<Station> stationList = new ArrayList<>();
        try(StationDao stationDao = DaoFactory.getInstance().getStationDao()){
            stationList = stationDao.getAll();
        }

        request.setAttribute("stations", stationList);
        return "user/home";
    }
}
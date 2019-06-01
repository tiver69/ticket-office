package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.facade.train.ShortTrainInfoFacade;
import ticketoffice.model.Station;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

public class FindTrainCommand implements Command {

    private static Logger LOG = Logger.getLogger(FindTrainCommand.class);
    private ShortTrainInfoFacade shortTrainInfoFacade = new ShortTrainInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {

        List<Station> stationList;
        try(StationDao stationDao = DaoFactory.getInstance().getStationDao()){
            stationList = stationDao.getAll();
        }

        int departureStation = Integer.parseInt(request.getParameter("departureStation"));
        int destinationStation = Integer.parseInt(request.getParameter("destinationStation"));
        Date date = Date.valueOf(request.getParameter("departureDate"));

        LOG.info(String.format("Search request for trains between %d -> %d on %s",
                departureStation, destinationStation, date.toString()));

        request.setAttribute("stations", stationList);
        request.setAttribute("trainsInformation",
                shortTrainInfoFacade.getRequestTrainInformation(departureStation, destinationStation, date));
        return "user/booking";
    }

}
package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.facade.train.ShortTrainInfoFacade;
import ticketoffice.model.Station;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Set request attribute "stations" with list of all stations for dropdown check item at page.
 * Get ID of departure station and destination station request, persist searching and set result to
 * request attribute "trainsInformation".
 * Forward to user/booking page if there was no errors.
 */
public class FindTrainCommand implements Command {

    private static Logger LOG = Logger.getLogger(FindTrainCommand.class);
    private ShortTrainInfoFacade shortTrainInfoFacade = new ShortTrainInfoFacade();
    private DateValidator dateValidator = new DateValidator();

    private List<Station> stationList;
    private int departureStation;
    private int destinationStation;
    private Date date;

    @Override
    public String execute(HttpServletRequest request) {

        String locale = (String) request.getSession().getAttribute("locale");
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationDao.setLocale(locale);
            stationList = stationDao.getAll();
            departureStation = Integer.parseInt(request.getParameter("departureStation"));
            destinationStation = Integer.parseInt(request.getParameter("destinationStation"));
            date = Date.valueOf(request.getParameter("departureDate"));

            validateRequest();
        } catch (ValidateFailException e) {
            request.setAttribute("errorCode", e.getErrorKeyMessage());
            return ("error/400");
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments in search parameters for /findTrain request");
            return ("error/400");
        }

        LOG.info(String.format("Search request for trains between %d -> %d on %s",
                departureStation, destinationStation, date.toString()));

        request.setAttribute("stations", stationList);
        request.setAttribute("trainsInformation",
                shortTrainInfoFacade.getRequestTrainInformation(departureStation, destinationStation, date, locale));

        return "user/booking";
    }

    private void validateRequest() throws ValidateFailException {
        int count = stationList.stream().filter(station ->
                station.getId() == departureStation || station.getId() == destinationStation)
                .collect(Collectors.toList()).size();
        if (count != 2) throw new ValidateFailException("station");
        dateValidator.validateDate(date);
    }
}

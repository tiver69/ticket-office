package ticketoffice.service.utils;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Station;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;

import java.util.Optional;

public class StationService {

    private static Logger LOG = Logger.getLogger(StationService.class);

    public Station getStation(int stationId, String locale) throws ValidateFailException {
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationDao.setLocale(locale);
            Optional<Station> station = stationDao.getById(stationId);
            if (station.isPresent())
                return station.get();
        }

        LOG.error(String.format("Station with id#%d doesn't exist", stationId));
        throw new ValidateFailException("station");
    }
}

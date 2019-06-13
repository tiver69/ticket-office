package ticketoffice.persistence.mapper;

import ticketoffice.model.Station;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.StationDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mapper for Station class, for extraction StationMapper item from db result set
 *
 * @value locale - specify language for extraction string columns
 */
public class StationMapper implements Mapper<Station> {

    private StationDao stationDao;
    private String locale;

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setDao(AbstractDao abstractDao) {
        this.stationDao = (StationDao) abstractDao;
    }

    @Override
    public Optional<Station> extractItem(ResultSet resultSet) throws SQLException {
        Station station = null;
        if (resultSet.first()) {
            station = new Station();
            station.setId(resultSet.getInt("id"));
            station.setName(resultSet.getString("name_" + locale));
        }
        return Optional.ofNullable(station);
    }

    @Override
    public ArrayList<Station> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Station> stationList = new ArrayList<>();
        while (resultSet.next()) {
            stationDao.getById(resultSet.getInt("id"))
                    .ifPresent(stationList::add);
        }
        return stationList;
    }
}
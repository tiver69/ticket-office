package ticketoffice.persistence.mapper;

import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class StationMapper implements Mapper<Station> {

    private StationDao stationDao;

    public void setDao(AbstractDao abstractDao) {
        this.stationDao = (StationDao) abstractDao;
    }

    @Override
    public Optional<Station> extractItem(ResultSet resultSet) throws SQLException {
        Station station = null;
        if (resultSet.first()) {
            station = new Station();
            station.setId(resultSet.getInt("id"));
            station.setName(resultSet.getString("name"));
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
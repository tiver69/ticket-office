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

public class StationMapper implements Mapper<Station> {

    private StationDao stationDao;

    public void setDao(AbstractDao abstractDao) {
        this.stationDao = (StationDao) abstractDao;
    }

    @Override
    public Station extractItem(ResultSet resultSet) throws SQLException {
        Station station = new Station();
        while (resultSet.next()) {
            station.setId(resultSet.getInt("id"));
            station.setName(resultSet.getString("name"));
        }
        return station;
    }

    @Override
    public ArrayList<Station> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Station> stationList = new ArrayList<>();
        while (resultSet.next()) {
            stationDao.getById(resultSet.getInt("id"))
                    .map(stationList::add);
//            Ticket ticket
//                    = ticketDao.getById(resultSet.getInt("id")).get();
//            ticketList.add(ticket);
        }
        return stationList;
    }
}
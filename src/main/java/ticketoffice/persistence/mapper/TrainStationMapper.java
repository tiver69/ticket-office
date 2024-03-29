package ticketoffice.persistence.mapper;

import ticketoffice.model.Station;
import ticketoffice.model.Train;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mapper for TrainStation class, for extraction TrainStation item from db result set.
 * Fills only id-field for related Train, Station values.
 */
public class TrainStationMapper implements Mapper<TrainStation> {

    private TrainStationDao trainStationDao;

    public void setDao(AbstractDao passengerDao) {
        this.trainStationDao = (TrainStationDao) passengerDao;
    }

    @Override
    public Optional<TrainStation> extractItem(ResultSet resultSet) throws SQLException {
        TrainStation trainStation = null;
        Station station = new Station();
        Train train = new Train();
        if (resultSet.first()) {
            trainStation = new TrainStation();
            station.setId(resultSet.getInt("station_id"));
            trainStation.setStation(station);
            train.setId(resultSet.getInt("train_id"));
            trainStation.setTrain(train);
            trainStation.setOrder(resultSet.getInt("order"));
            trainStation.setPrise(resultSet.getInt("prise"));
            trainStation.setArrivalTime(resultSet.getTime("arrival"));
            trainStation.setDepartureTime(resultSet.getTime("departure"));
        }
        return Optional.ofNullable(trainStation);
    }

    @Override
    public ArrayList<TrainStation> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<TrainStation> trainStationList = new ArrayList<>();
        while (resultSet.next()) {
            trainStationDao.getByTrainIdAndStationId(resultSet.getInt("station_id"),
                    resultSet.getInt("train_id"))
                    .ifPresent(trainStationList::add);
        }
        return trainStationList;
    }
}

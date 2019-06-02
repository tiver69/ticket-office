package ticketoffice.persistence.mapper;

import ticketoffice.model.*;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrainStationMapper implements Mapper<TrainStation> {

    private TrainStationDao trainStationDao;

    public void setDao(AbstractDao passengerDao) {
        this.trainStationDao = (TrainStationDao) passengerDao;
    }

    @Override
    public TrainStation extractItem(ResultSet resultSet) throws SQLException {
        TrainStation trainStation = new TrainStation();
        Station station = new Station();
        Train train = new Train();

        while (resultSet.next()) {
            station.setId(resultSet.getInt("station_id"));
            trainStation.setStation(station);
            train.setId(resultSet.getInt("train_id"));
            trainStation.setTrain(train);
            trainStation.setOrder(resultSet.getInt("order"));
            trainStation.setPrise(resultSet.getInt("prise"));
            trainStation.setArrivalTime(resultSet.getTime("arrival"));
            trainStation.setDepartureTime(resultSet.getTime("departure"));
        }
        return trainStation;
    }

    @Override
    public ArrayList<TrainStation> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<TrainStation> trainStationList = new ArrayList<>();
        while (resultSet.next()) {
            trainStationDao.getByTrainIdAndStationId(resultSet.getInt("station_id"),
                    resultSet.getInt("train_id"))
                    .map(trainStationList::add);
        }
        return trainStationList;
    }
}

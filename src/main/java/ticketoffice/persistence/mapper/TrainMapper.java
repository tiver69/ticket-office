package ticketoffice.persistence.mapper;

import ticketoffice.model.Train;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrainMapper implements Mapper<Train> {

    private TrainDao trainDao;

    public void setDao(AbstractDao trainDao) {
        this.trainDao = (TrainDao)trainDao;
    }

    @Override
    public Train extractItem(ResultSet resultSet) throws SQLException {
        Train train = new Train();
        while (resultSet.next()) {
            train.setId(resultSet.getInt("id"));
            train.setFrequency(resultSet.getInt("frequency"));
            train.setMarkup(resultSet.getInt("markup"));
        }
        return train;
    }

    @Override
    public ArrayList<Train> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Train> trainList = new ArrayList<>();
        while (resultSet.next()) {
            Train train
                    = trainDao.getById(resultSet.getInt("id"));
            trainList.add(train);
        }
        return trainList;
    }
}

package ticketoffice.persistence.mapper;

import ticketoffice.model.CoachType;
import ticketoffice.model.Train;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mapper for TrainCoach class, for extraction TrainCoach item from db result set.
 * Fills only id-field for related Train, CoachType values.
 */
public class TrainCoachMapper implements Mapper<TrainCoach> {

    private TrainCoachDao trainCoachDao;

    public void setDao(AbstractDao abstractDao) {
        this.trainCoachDao = (TrainCoachDao) abstractDao;
    }

    @Override
    public Optional<TrainCoach> extractItem(ResultSet resultSet) throws SQLException {
        TrainCoach trainCoach = null;
        Train train = new Train();
        CoachType coachType = new CoachType();
        if (resultSet.first()) {
            trainCoach = new TrainCoach();
            trainCoach.setId(resultSet.getInt("id"));
            train.setId(resultSet.getInt("train_id"));
            trainCoach.setTrain(train);
            coachType.setId(resultSet.getInt("coach_type_id"));
            trainCoach.setCoachType(coachType);
            trainCoach.setNumber(resultSet.getInt("number"));
        }
        return Optional.ofNullable(trainCoach);
    }

    @Override
    public ArrayList<TrainCoach> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<TrainCoach> trainCoachList = new ArrayList<>();
        while (resultSet.next()) {
            trainCoachDao.getById(resultSet.getInt("id"))
                    .ifPresent(trainCoachList::add);
        }
        return trainCoachList;
    }
}

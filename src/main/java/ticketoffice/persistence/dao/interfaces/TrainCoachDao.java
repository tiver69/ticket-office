package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.TrainCoach;

import java.util.List;

public interface TrainCoachDao extends AbstractDao<TrainCoach> {

    List<TrainCoach> getByTrainId(int trainId);

}

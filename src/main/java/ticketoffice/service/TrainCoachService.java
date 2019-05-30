package ticketoffice.service;

import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;

public class TrainCoachService {

    public void fillTrainCoach(TrainCoach trainCoach) {
        try (CoachTypeDao coachTypeDao = DaoFactory.getInstance().getCoachTypeDao()) {
            coachTypeDao.getById(trainCoach.getCoachType().getId())
                    .ifPresent(
                            trainCoach::setCoachType
                    );
        }
    }
}

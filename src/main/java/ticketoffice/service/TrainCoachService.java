package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;

public class TrainCoachService {

    private static Logger LOG = Logger.getLogger(TrainCoachService.class);

    public void fillTrainCoach(TrainCoach trainCoach) {
        try (CoachTypeDao coachTypeDao = DaoFactory.getInstance().getCoachTypeDao()) {
            coachTypeDao.getById(trainCoach.getCoachType().getId())
                    .ifPresent(
                            trainCoach::setCoachType
                    );
        }
    }
}

package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

public class TrainCoachService {

    private static Logger LOG = Logger.getLogger(TrainCoachService.class);

    public void fillTrainCoachType(TrainCoach trainCoach) {
        try (CoachTypeDao coachTypeDao = DaoFactory.getInstance().getCoachTypeDao()) {
            coachTypeDao.getById(trainCoach.getCoachType().getId())
                    .ifPresent(
                            trainCoach::setCoachType
                    );
            LOG.debug(String.format("Fill trainCoach#%d with %s(#%d) type", trainCoach.getId(),
                    trainCoach.getCoachType().getName(), trainCoach.getCoachType().getId()));
        }
    }

    public void fillTrainCoachTrain(TrainCoach trainCoach){
        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()) {
            trainDao.getById(trainCoach.getTrain().getId())
                    .ifPresent(
                            trainCoach::setTrain
                    );
            LOG.debug(String.format("Fill trainCoach#%d with #%d train", trainCoach.getId(),
                    trainCoach.getTrain().getId()));
        }
    }
}

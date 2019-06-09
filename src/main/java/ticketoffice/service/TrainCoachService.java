package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

import java.util.Optional;

public class TrainCoachService {

    private static Logger LOG = Logger.getLogger(TrainCoachService.class);
    private CoachTypeService coachTypeService = new CoachTypeService();

    public TrainCoach getTrainCoach(int trainCoachId) throws ValidateFailException {
        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()) {
            Optional<TrainCoach> trainCoach = trainCoachDao.getById(trainCoachId);
            if (trainCoach.isPresent()) {
                fillTrainCoachType(trainCoach.get());
                return trainCoach.get();
            }
        }

        LOG.error(String.format("Coach #%d doesn't exist", trainCoachId));
        throw new ValidateFailException("coach");
    }

    public void fillTrainCoachType(TrainCoach trainCoach) {
        trainCoach.setCoachType(coachTypeService.getCoachType(trainCoach.getCoachType().getId()));
        LOG.debug(String.format("Fill trainCoach#%d with %s(#%d) type", trainCoach.getId(),
                trainCoach.getCoachType().getName(), trainCoach.getCoachType().getId()));
    }
}

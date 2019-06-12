package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;

import java.util.Optional;

public class TrainCoachService {

    private static Logger LOG = Logger.getLogger(TrainCoachService.class);
    private CoachTypeService coachTypeService = new CoachTypeService();

    /**
     * @return extracted TrainCoach with requested locale parameter from Optional value.
     * @throws ValidateFailException in case item was not found.
     */
    public TrainCoach getTrainCoach(int trainCoachId, String locale) throws ValidateFailException {
        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()) {
            Optional<TrainCoach> trainCoach = trainCoachDao.getById(trainCoachId);
            if (trainCoach.isPresent()) {
                fillTrainCoachType(trainCoach.get(), locale);
                return trainCoach.get();
            }
        }

        LOG.error(String.format("Coach #%d doesn't exist", trainCoachId));
        throw new ValidateFailException("coach");
    }


    /**
     * Get all additional information for trainCoach fields and set extracted values to
     * request trainCoach parameter.
     */
    public void fillTrainCoachType(TrainCoach trainCoach, String locale) {
        trainCoach.setCoachType(coachTypeService.getCoachType(trainCoach.getCoachType().getId(), locale));
        LOG.debug(String.format("Fill trainCoach#%d with %s(#%d) type", trainCoach.getId(),
                trainCoach.getCoachType().getName(), trainCoach.getCoachType().getId()));
    }
}

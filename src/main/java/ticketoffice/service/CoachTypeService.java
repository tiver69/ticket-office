package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.CoachType;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;

import java.util.Optional;

public class CoachTypeService {

    private static Logger LOG = Logger.getLogger(CoachTypeService.class);

    /**
     * @return extracted CoachType with requested locale parameter from Optional value.
     * @throws ValidateFailException in case item was not found.
     */
    public CoachType getCoachType(int coachTypeId, String locale) throws ValidateFailException {
        try (CoachTypeDao coachTypeDao = DaoFactory.getInstance().getCoachTypeDao()) {
            coachTypeDao.setLocale(locale);
            Optional<CoachType> coachType = coachTypeDao.getById(coachTypeId);
            if (coachType.isPresent())
                return coachType.get();
        }

        LOG.error(String.format("Coach type #%d doesn't exist", coachTypeId));
        throw new ValidateFailException("coach");
    }
}

package ticketoffice.validator;

import org.apache.log4j.Logger;
import ticketoffice.commands.FindTrainCommand;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;

/**
 * Class for validate requested Data values before extraction db information.
 */
public class DateValidator {

    private static Logger LOG = Logger.getLogger(FindTrainCommand.class);

    /**
     * Validate date as its value must be valid according both past and future parameters.
     *
     * @param date - requested Date
     * @throws ValidateFailException - in case date is from past OR from the future.
     */
    public void validateDate(Date date) throws ValidateFailException {
        validatePastDate(date);
        validateFutureDate(date);
    }

    /**
     * Validate date as its value must not be earlier that current calendar day.
     *
     * @param date - requested Date
     * @throws ValidateFailException - in case date is from past
     */
    private void validatePastDate(Date date) throws ValidateFailException {
        if (date.compareTo(new Date(System.currentTimeMillis() - TimeDateUtil.ONE_DAY)) < 0) {
            LOG.error(String.format("Request date %s is from the past", date.toString()));
            throw new ValidateFailException("time.past");
        }
    }

    /**
     * Validate date as its value must not be later that 180 calendar days from
     * current day.
     *
     * @param date - requested Date
     * @throws ValidateFailException - in case date is later than 180 days in future.
     */
    private void validateFutureDate(Date date) throws ValidateFailException {
        if (date.compareTo(TimeDateUtil.getThreeMonthLater()) > 0) {
            LOG.error(String.format("Request date %s is more than thee month later", date.toString()));
            throw new ValidateFailException("time.future");
        }
    }
}

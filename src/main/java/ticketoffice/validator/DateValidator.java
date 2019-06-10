package ticketoffice.validator;

import org.apache.log4j.Logger;
import ticketoffice.commands.FindTrainCommand;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;

public class DateValidator {

    private static Logger LOG = Logger.getLogger(FindTrainCommand.class);

    public void validateDate(Date date) throws ValidateFailException {
        validatePastDate(date);
        validateFutureDate(date);
    }

    private void validatePastDate(Date date) throws ValidateFailException {
        if (date.compareTo(new Date(System.currentTimeMillis() - TimeDateUtil.ONE_DAY)) < 0) {
            LOG.error(String.format("Request date %s is from the past", date.toString()));
            throw new ValidateFailException("time.past");
        }
    }

    private  void validateFutureDate(Date date) throws ValidateFailException {
        if (date.compareTo(TimeDateUtil.getThreeMonthLater())>0){
            LOG.error(String.format("Request date %s is more than thee month later", date.toString()));
            throw new ValidateFailException("time.future");
        }
    }
}

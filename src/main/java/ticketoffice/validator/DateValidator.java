package ticketoffice.validator;

import org.apache.log4j.Logger;
import ticketoffice.commands.FindTrainCommand;
import ticketoffice.exceptions.ValidateFailException;

import java.sql.Date;

public class DateValidator {

    private static Logger LOG = Logger.getLogger(FindTrainCommand.class);

    public void validatePastDate(Date date) throws ValidateFailException{
        if (date.compareTo(new Date(System.currentTimeMillis())) < 0) {
            LOG.error(String.format("Request date %s is from the past", date.toString()));
            throw new ValidateFailException("time.past");
        }
    }
}

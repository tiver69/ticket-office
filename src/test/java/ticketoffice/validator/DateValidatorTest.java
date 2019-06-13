package ticketoffice.validator;

import org.junit.Assert;
import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;

import java.sql.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DateValidatorTest {

    private Date pastDate = Date.valueOf("1999-03-17");
    private Date futureDate = Date.valueOf("3000-01-01");
    private DateValidator dateValidator = new DateValidator();

    @Test(expected = ValidateFailException.class)
    public void shouldThrowPastException() {
        dateValidator.validateDate(pastDate);
    }

    @Test(expected = ValidateFailException.class)
    public void shouldThrowFutureException() {
        dateValidator.validateDate(futureDate);
    }
}

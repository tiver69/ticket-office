package ticketoffice.validator;

import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;

import java.sql.Date;

import static org.junit.Assert.*;

public class PassengerValidatorTest {

    private PassengerValidator passengerValidator = new PassengerValidator();
    private String firstName = "Yep";
    private String lastName = "Yep";
    private String lastNameIncorrect = "nope";
    private String login = "To_big_login_test";

    @Test(expected = ValidateFailException.class)
    public void shouldThrowExceptionOnLogin() {
        passengerValidator.validatePassengerInfo(firstName, lastName, login);
    }

    @Test(expected = ValidateFailException.class)
    public void validateNewPassengerLogin() {
        passengerValidator.validateNewPassengerLogin("tiver69");
    }

    @Test(expected = ValidateFailException.class)
    public void validateNewPassengerInfo() {
        passengerValidator.validateNewPassengerInfo(firstName, lastNameIncorrect, login);
    }
}
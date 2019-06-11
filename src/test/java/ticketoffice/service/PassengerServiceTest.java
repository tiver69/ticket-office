package ticketoffice.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PassengerServiceTest {

    private static PassengerService passengerService = new PassengerService();
    private static int PASSENGER_ID = 1703;
    private static String PASSENGER_LOGIN = "testLogin";

    @Test(expected = ValidateFailException.class)
    public void getPassenger() {
        passengerService.getPassenger(PASSENGER_ID);
    }

    @Test
    public void loginPassengerTest() {
        Optional<Passenger> passenger
                = passengerService.loginPassenger(PASSENGER_LOGIN, "not_exist");
        Assert.assertThat("Passenger with such login doesn't exist",
                passenger, equalTo(Optional.empty()));
    }
}

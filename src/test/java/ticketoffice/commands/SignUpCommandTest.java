package ticketoffice.commands;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ticketoffice.dto.UserDto;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.mapper.PassengerMapper;
import ticketoffice.service.PassengerService;
import ticketoffice.validator.PassengerValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpCommandTest {

    @InjectMocks
    private Command command = new SignUpCommand();
    @Mock
    private HttpServletRequest request;
    @Mock
    private PassengerMapper passengerMapper;
    @Mock
    private PassengerService passengerService;
    @Mock
    private PassengerValidator passengerValidator;

    @Before
    public void setUp() {
        when(passengerMapper.extractItemFromRequest(request)).thenReturn(new Passenger());
        when(passengerService.registerPassenger(anyObject())).thenReturn(true);
    }

    @Test
    public void executeSuccessSignUpCommandTest() {
        doNothing().when(passengerValidator).validateNewPassengerInfo(anyString(), anyString(), anyString());
        Assert.assertThat("User must be successfully sigh up",
                command.execute(request), equalTo("redirect/login"));
    }

    @Test
    public void executeFailSignUpCommandTest() {
        doThrow(ValidateFailException.class).when(passengerValidator)
                .validateNewPassengerInfo(anyString(), anyString(), anyString());
        Assert.assertThat("User mustn't be successfully sigh up",
                command.execute(request), equalTo("error/400"));
    }
}
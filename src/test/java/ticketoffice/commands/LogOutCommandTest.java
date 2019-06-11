package ticketoffice.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import ticketoffice.commands.page.UserListPageCommand;
import ticketoffice.dto.UserDto;
import ticketoffice.facade.PassengerInfoFacade;
import ticketoffice.model.Passenger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogOutCommandTest {

    @InjectMocks
    private Command command = new LogOutCommand();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;


    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void executeLogoutCommandTest() {
        command.execute(request);
        verify(request, times(3)).getSession();
    }
}
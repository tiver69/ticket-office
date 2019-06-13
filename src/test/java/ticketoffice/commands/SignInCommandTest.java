package ticketoffice.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ticketoffice.dto.UserDto;
import ticketoffice.facade.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignInCommandTest {

    private String login = "login";
    private String password = "password";

    @InjectMocks
    private Command command = new SignInCommand();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;
    @Mock
    private UserFacade userFacade;

    @Before
    public void setUp() {
        when(request.getParameter(login)).thenReturn(login);
        when(request.getParameter(password)).thenReturn(password);
        when(userFacade.loadUser(login, password))
                .thenReturn(Optional.of(new UserDto()));
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void executeSignInCommandTest() {
        command.execute(request);
        verify(request, times(1)).getSession();
    }
}
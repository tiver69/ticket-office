package ticketoffice.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherServletTest {

    private DispatcherServlet dispatcherServlet;

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        dispatcherServlet = new DispatcherServlet();
        when(request.getContextPath()).thenReturn("/ticket-office");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void shouldSendForward() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/ticket-office/page/home");
        dispatcherServlet.doPost(request,response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldSendRedirect() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/ticket-office/page/admin/home");
        dispatcherServlet.doPost(request,response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldSendError() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/ticket-office/page/unknown");
        dispatcherServlet.doPost(request,response);
        verify(response).setStatus(404);
        verify(response).sendError(404);
    }
}
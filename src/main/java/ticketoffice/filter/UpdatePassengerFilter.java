package ticketoffice.filter;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdatePassengerFilter implements Filter {

    private static Logger LOG = Logger.getLogger(UpdatePassengerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        UserDto user = (UserDto) httpRequest.getSession().getAttribute("user");
        int passengerId = Integer.parseInt(httpRequest.getParameter("passengerId"));
        boolean updateSuccessStatus = httpRequest.getAttribute("updateSuccess") == null ?
                false : (boolean) httpRequest.getAttribute("updateSuccess");

        if (passengerId == user.getPassenger ().getId() && updateSuccessStatus) {
            user.getPassenger().setFirstName(httpRequest.getParameter("passengerFirstName"));
            user.getPassenger().setLastName(httpRequest.getParameter("passengerLastName"));
            user.getPassenger().setLogin(httpRequest.getParameter("passengerLogin"));
            httpRequest.getSession().removeAttribute("user");
            httpRequest.getSession().setAttribute("user", user);
            LOG.info("Current session user was updated");
        }
    }

    @Override
    public void destroy() {

    }
}

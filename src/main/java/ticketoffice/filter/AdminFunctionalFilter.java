package ticketoffice.filter;

import ticketoffice.dto.UserDto;
import ticketoffice.model.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter for all app pages, requesting current user roles, and setting "true" bool-attribute to request
 * if current user has ADMIN role, and "false" if it does not.
 */
public class AdminFunctionalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (httpRequest.getSession().getAttribute("user") != null) {
            UserDto user = (UserDto) httpRequest.getSession().getAttribute("user");
            boolean adminFunctional = user.getRoles().contains(Role.ADMIN);
            servletRequest.setAttribute("adminFunctional", adminFunctional);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

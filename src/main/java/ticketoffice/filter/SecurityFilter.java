package ticketoffice.filter;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;
import ticketoffice.model.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter implements Filter {

    private static Logger LOG = Logger.getLogger(SecurityFilter.class);
    private String USER_PATTERN = "user/";
    private String ADMIN_PATTERN = "admin/";
    private List<String> GUEST_PATTERN = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        GUEST_PATTERN.add("registration");
        GUEST_PATTERN.add("signup");
        GUEST_PATTERN.add("login");
        GUEST_PATTERN.add("signin");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        UserDto user = (UserDto) httpRequest.getSession().getAttribute("user");
        Role requiredRole = getRequiredRole(httpRequest);

        if(requiredRole == null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (user != null
                && user.getRoles().contains(requiredRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (user == null
                && requiredRole == Role.GUEST) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        LOG.error("Unauthorized request was blocked");
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void destroy() {

    }

    private Role getRequiredRole(HttpServletRequest request) {
        String action = request.getRequestURI()
                .substring(request.getContextPath().length())
                .replaceAll("/page/", "");
        return
                action.startsWith(USER_PATTERN) ? Role.USER :
                        action.startsWith(ADMIN_PATTERN) ? Role.ADMIN :
                                GUEST_PATTERN.contains(action)? Role.GUEST :
                null;
    }
}
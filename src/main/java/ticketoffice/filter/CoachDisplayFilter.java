package ticketoffice.filter;

import org.apache.log4j.Logger;
import ticketoffice.facade.train.FullTrainInfoFacade;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CoachDisplayFilter implements Filter {

    private static Logger LOG = Logger.getLogger(CoachDisplayFilter.class);
    private FullTrainInfoFacade fullTrainInfoFacade = new FullTrainInfoFacade();
    private int currentCoachDefault;
    private static final String CURRENT_COACH = "currentCoach";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        currentCoachDefault = Integer.parseInt(
                filterConfig.getInitParameter(CURRENT_COACH));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        int currentCoach = servletRequest.getParameter("currentCoach") == null ?
                currentCoachDefault :
                Integer.parseInt(servletRequest.getParameter("currentCoach"));

        LOG.info(String.format("Request for #%s coach information: ", currentCoach));

        servletRequest.setAttribute("currentCoach", currentCoach);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

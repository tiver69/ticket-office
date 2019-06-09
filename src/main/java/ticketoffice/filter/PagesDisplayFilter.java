package ticketoffice.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PagesDisplayFilter implements Filter {

    private static Logger LOG = Logger.getLogger(PagesDisplayFilter.class);
    private static final String CURRENT_PAGE = "currentPage";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        int currentPage = servletRequest.getParameter(CURRENT_PAGE) == null ?
                1 :
                Integer.parseInt(servletRequest.getParameter(CURRENT_PAGE));
        currentPage = currentPage <= 0 ? 1 : currentPage;

        LOG.info(String.format("Request for %d-%d items of information: ", (currentPage - 1) * 5,
                (currentPage - 1) * 5 + 5));

        servletRequest.setAttribute(CURRENT_PAGE, currentPage);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

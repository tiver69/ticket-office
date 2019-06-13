package ticketoffice.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter for pages supporting pagination, setting current page value
 * to current request "currentPage" parameter:
 * with parameter from command if present,
 * with default value(1) parameter in case of null "currentPage" parameter in received request,
 * with value(1) parameter in case of "currentPage" parameter was lesser than 0 in received request.
 */
public class PagesDisplayFilter implements Filter {

    private static Logger LOG = Logger.getLogger(PagesDisplayFilter.class);
    private static final String CURRENT_PAGE = "currentPage";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
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

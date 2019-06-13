package ticketoffice.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter for all app pages, setting locale and bundle variable to current session "locale"
 * and "bundle" attribute:
 * with parameter from command in case it was request from command for change,
 * with default value parameter in case of null locale attribute in current session.
 */
public class LocalizationFilter implements Filter {

    private static Logger LOG = Logger.getLogger(LocalizationFilter.class);

    private static final String LOCALE = "locale";
    private static final String BUNDLE = "bundle";
    private String defaultBundle;
    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultBundle = filterConfig.getInitParameter(BUNDLE);
        defaultLocale = filterConfig.getInitParameter(LOCALE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String localeParameter = servletRequest.getParameter(LOCALE);

        if (localeParameter != null) {
            httpRequest.getSession().setAttribute(LOCALE, localeParameter);
            LOG.info("Set locale to " + localeParameter);
        } else if (httpRequest.getSession().getAttribute(LOCALE) == null) {
            httpRequest.getSession().setAttribute(LOCALE, defaultLocale);
            LOG.info("Set locale to default " + defaultLocale);
        }

        if (httpRequest.getSession().getAttribute(BUNDLE) == null) {
            httpRequest.getSession().setAttribute(BUNDLE, defaultBundle);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

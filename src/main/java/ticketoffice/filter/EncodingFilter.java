package ticketoffice.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for all app pages, setting character encoding and content types to response.
 */
public class EncodingFilter implements Filter {

    private static final String ENCODING_TYPE = "encoding_type";
    private String encodingTypeValue;

    @Override
    public void init(FilterConfig filterConfig){
        encodingTypeValue = filterConfig.getInitParameter(ENCODING_TYPE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encodingTypeValue);
        servletResponse.setCharacterEncoding(encodingTypeValue);
        servletResponse.setContentType("text/html; charset" + encodingTypeValue);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

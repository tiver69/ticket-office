package ticketoffice.web;

import org.apache.log4j.Logger;
import ticketoffice.commands.Command;
import ticketoffice.commands.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    private static Logger LOG = Logger.getLogger(DispatcherServlet.class);

    private static final String JSP_PATH = "/jsp/%s.jsp";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doGetAndPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGetAndPost(request, response);
    }

    private void doGetAndPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getRequestURI()
                .substring(request.getContextPath().length())
                .replaceAll("/page/", "");

        Command command = CommandFactory.getInstance().getCommand(action);
        String page = command.execute(request);

        if (page.startsWith("redirect/")){
            String redirectUrl = request.getContextPath()+ "/page/" + page.replace("redirect/", "");
            LOG.info("Redirect to " + redirectUrl
                    + " page after /" + action + " request");
            response.sendRedirect(redirectUrl);
        }
        else {
            LOG.info("Forward to " + page + ".jsp page after /" + action + " request");
            request.getRequestDispatcher(String.format(JSP_PATH, page))
                    .forward(request, response);
        }
    }
}
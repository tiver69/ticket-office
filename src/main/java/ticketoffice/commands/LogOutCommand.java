package ticketoffice.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Remove user attribute from current session and invalidate it.
 * Redirect to home page.
 */
public class LogOutCommand implements Command {

    private static Logger LOG = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.info(String.format("User %s successfully logout",
                request.getSession().getAttribute("user")));

        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        return "redirect/home";
    }

}
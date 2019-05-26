package ticketoffice.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    private static Logger LOG = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.info(String.format("User %s successfully logout",
                request.getSession().getAttribute("userId")));

        request.getSession().removeAttribute("user");
        return "index";
    }

}
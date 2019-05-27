package ticketoffice.commands;

import ticketoffice.facade.UserFacade;
import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {

    private static Logger LOG = Logger.getLogger(SignInCommand.class);
    private UserFacade userFacade = new UserFacade();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        LOG.info(String.format("Get login request from %s", login));

        UserDto user = userFacade.loadUser(
                login, request.getParameter("password"));
        if (user != null) {
            request.getSession().setAttribute("user", user);
            LOG.info("Successful sign in from " + login);
            return "/user/home";
        }

        LOG.info("Fail to sign in " + login);
        return "login";
    }
}
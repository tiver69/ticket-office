package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.service.PassengerService;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {

    private static Logger LOG = Logger.getLogger(SignInCommand.class);
    private PassengerService passengerService = new PassengerService();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        LOG.info(String.format("Get login request from %s", login));

        int userId = passengerService.loginPassenger(
                login, request.getParameter("password"));
        if (userId != 0) {
            request.getSession().setAttribute("userId",userId);
            LOG.info("Successful sign in from " + login);
            return "/user/home";
        }

        LOG.info("Fail to sign in " + login);
        return "login";
    }
}
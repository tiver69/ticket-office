package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;
import ticketoffice.facade.UserFacade;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SignInCommand implements Command {

    private static Logger LOG = Logger.getLogger(SignInCommand.class);
    private UserFacade userFacade = new UserFacade();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        LOG.info(String.format("Get login request from %s", login));

        Optional<UserDto> user = userFacade.loadUser(
                login, request.getParameter("password"));
        if (user.isPresent()) {
            request.getSession().setAttribute("user", user.get());
            return "redirect/user/home";
        }

        return "redirect/login";
    }
}

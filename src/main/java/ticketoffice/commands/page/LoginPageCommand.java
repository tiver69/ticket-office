package ticketoffice.commands.page;

import ticketoffice.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Forward to /login page.
 */
public class LoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "login";
    }
}
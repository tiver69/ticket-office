package ticketoffice.commands.page;

import ticketoffice.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Redirect to /user/home page.
 */
public class AdminMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "redirect/user/home";
    }
}

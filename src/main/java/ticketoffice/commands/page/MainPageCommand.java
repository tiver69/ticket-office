package ticketoffice.commands.page;

import ticketoffice.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Forward to /index page.
 */
public class MainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "index";
    }
}

package ticketoffice.commands;

import javax.servlet.http.HttpServletRequest;

public class UnknownPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "unknown-page.html";
    }
}

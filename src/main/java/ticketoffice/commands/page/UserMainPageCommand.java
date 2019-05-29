package ticketoffice.commands.page;

import ticketoffice.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class UserMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "user/home";
    }
}
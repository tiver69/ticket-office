package ticketoffice.commands.page;

import ticketoffice.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "registration";
    }
}
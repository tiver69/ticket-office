package ticketoffice.commands;

import ticketoffice.commands.page.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Return relevant command implementation according to request action.
 */
public class CommandFactory {

    private Map<String, Command> commandMap;

    private CommandFactory() {
        commandMap = new HashMap<>();
        commandMap.put("home", new MainPageCommand());
        commandMap.put("registration", new RegistrationPageCommand());
        commandMap.put("signup", new SignUpCommand());
        commandMap.put("login", new LoginPageCommand());
        commandMap.put("signin", new SignInCommand());

        commandMap.put("user/home", new UserMainPageCommand());
        commandMap.put("user/logout", new LogOutCommand());
        commandMap.put("user/findTrain", new FindTrainCommand());
        commandMap.put("user/trainDetail", new TrainDetailsCommand());
        commandMap.put("user/ticketDetail", new TicketDetailsCommand());
        commandMap.put("user/saveTicket", new SaveTicketCommand());
        commandMap.put("user/returnTicket", new ReturnTicketCommand());

        commandMap.put("admin/home", new AdminMainPageCommand());
        commandMap.put("admin/users", new UserListPageCommand());
        commandMap.put("admin/promoteToAdmin", new PromoteToAdminCommand());
        commandMap.put("admin/removePassenger", new RemoveUserCommand());
        commandMap.put("admin/updatePassenger", new UpdatePassengerCommand());
    }

    private static class SingletonDaoFactory {
        private final static CommandFactory daoFactory = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return SingletonDaoFactory.daoFactory;
    }

    public Command getCommand(String action) {
        return commandMap.getOrDefault(action, new NotFoundPageCommand());
    }
}

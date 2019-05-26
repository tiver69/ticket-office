package ticketoffice.commands;

import ticketoffice.commands.page.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap;

    private CommandFactory() {
        commandMap = new HashMap<>();
        commandMap.put("main", new MainPageCommand());
        commandMap.put("registration", new RegistrationPageCommand());
        commandMap.put("signup", new SignUpCommand());
        commandMap.put("login", new LoginPageCommand());
        commandMap.put("signin", new SignInCommand());
        commandMap.put("logout", new LogOutCommand());

        commandMap.put("user/home", new UserMainPageCommand());
    }

    private static class SingletonDaoFactory {
        private final static CommandFactory daoFactory = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return SingletonDaoFactory.daoFactory;
    }

    public Command getCommand(String action) {
        return commandMap.getOrDefault(action, new UnknownPageCommand());
    }
}

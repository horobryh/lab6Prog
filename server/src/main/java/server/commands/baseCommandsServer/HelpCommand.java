package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import server.commands.CommandManager;
import server.commands.Executable;

/**
 * Help print command class
 */
public class HelpCommand implements Executable {
    private final CommandManager commandManager;

    @Override
    public Response execute(Request request) {
        for (Executable command : commandManager.getCommands().values()) {
            if (command.getName() != null) {
                System.out.println(command.getName() + " " + command.getArgs() + "\t" + command.getDescription());
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Вывести справку по доступным командам";
    }

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}

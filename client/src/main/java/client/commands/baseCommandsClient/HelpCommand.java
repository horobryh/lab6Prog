package client.commands.baseCommandsClient;

import client.commands.CommandManager;
import client.commands.Executable;

import java.util.Scanner;

/**
 * Help print command class
 */
public class HelpCommand implements Executable {
    private final CommandManager commandManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        for (Executable command: commandManager.getCommands().values()) {
            System.out.println(command.getName() + " " + command.getArgs() + "\t" + command.getDescription());
        }
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

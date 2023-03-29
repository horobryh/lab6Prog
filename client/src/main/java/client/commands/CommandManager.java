package client.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Command manager class that stores references to all commands
 */
public class CommandManager {
    private static HashMap<String, Executable> commands = new HashMap<>();
    private static CommandManager instance = null;

    public static CommandManager getInstance() {
        if (instance==null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public void regNewCommand(String key, Executable command) {
        commands.put(key, command);
    }

    public void executeCommand (String[] args, Scanner... scanners) throws IllegalArgumentException {
        Scanner scanner = scanners.length > 0 ? scanners[0] : new Scanner(System.in);
        Executable command = commands.get(args[0]);
        if (command == null) {
            throw new IllegalArgumentException("Введенной команды не существует.");
        }
        command.execute(Arrays.copyOfRange(args, 1, args.length), scanner);
    }

    public HashMap<String, Executable> getCommands() {
        return commands;
    }
}

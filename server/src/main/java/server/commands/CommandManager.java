package server.commands;

import general.network.Request;
import general.network.Response;
import server.network.RequestManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Command manager class that stores references to all commands
 */
public class CommandManager {
    private static HashMap<String, Executable> commands = new HashMap<>();
    private static CommandManager instance = null;
    private RequestManager requestManager;

    private CommandManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public static CommandManager getInstance(RequestManager requestManager) {
        if (instance==null) {
            instance = new CommandManager(requestManager);
        }
        return instance;
    }

    public void regNewCommand(String key, Executable command) {
        commands.put(key, command);
    }

    public Response executeCommand (Request request) throws IllegalArgumentException {
        return commands.get(requestManager.getCommandKeyByRequestClass(request.getClass())).execute(request);
        // TODO: исключения?
//        Scanner scanner = scanners.length > 0 ? scanners[0] : new Scanner(System.in);
//        Executable command = commands.get(args[0]);
//        if (command == null) {
//            throw new IllegalArgumentException("Введенной команды не существует.");
//        }
//        command.execute(Arrays.copyOfRange(args, 1, args.length), scanner);
    }

    public HashMap<String, Executable> getCommands() {
        return commands;
    }
}

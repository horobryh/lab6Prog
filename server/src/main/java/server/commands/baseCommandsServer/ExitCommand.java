package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import server.commands.Executable;

import java.util.Scanner;

/**
 * Program exit command class
 */
public class ExitCommand implements Executable {
    @Override
    public Response execute(Request request) {
        System.exit(0);
        return null;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}

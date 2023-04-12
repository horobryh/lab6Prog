package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.ClearResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;

import java.util.Scanner;

/**
 * Collection cleanup command class
 */
public class ClearCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        collectionManager.clear();
        return new ClearResponse(true);
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

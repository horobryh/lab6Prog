package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.CheckIDInCollectionResponse;
import general.network.responses.ClearResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import server.network.Server;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Collection cleanup command class
 */
public class ClearCommand implements Executable {
    private final CollectionManager collectionManager;
    private Server server;
    @Override
    public Response execute(Request request) {
        try {
            if (server.getDataBaseManager().clear() > 0) {
                collectionManager.clear();
                return new ClearResponse(true);
            }
        } catch (SQLException e) {
            return new ClearResponse(false, e.getMessage());
        }
        return new ClearResponse(false);
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

    public ClearCommand(CollectionManager collectionManager, Server server) {
        this.collectionManager = collectionManager;
        this.server = server;
    }
}

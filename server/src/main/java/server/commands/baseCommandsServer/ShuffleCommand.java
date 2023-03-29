package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.ShuffleResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;

/**
 * Command class that randomly shuffles a collection
 */
public class ShuffleCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        collectionManager.shuffle();
        System.out.println("Коллекция перемешана.");
        return new ShuffleResponse(true);
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }

    public ShuffleCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.SortResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;

/**
 * Command class that sorts a collection
 */
public class SortCommand implements Executable {
    private final CollectionManager collectionManager;

    @Override
    public Response execute(Request request) {
        collectionManager.sortCollection();
        return new SortResponse(true, collectionManager.getCollection());
    }

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в естественном порядке";
    }

    public SortCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

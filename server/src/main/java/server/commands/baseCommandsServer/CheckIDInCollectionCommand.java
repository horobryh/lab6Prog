package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.requests.CheckIDInCollectionRequest;
import general.network.responses.CheckIDInCollectionResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;

public class CheckIDInCollectionCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        Integer id = ((CheckIDInCollectionRequest) request).getId();
        boolean result = collectionManager.checkIDInCollection(id);
        return new CheckIDInCollectionResponse(result);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public CheckIDInCollectionCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

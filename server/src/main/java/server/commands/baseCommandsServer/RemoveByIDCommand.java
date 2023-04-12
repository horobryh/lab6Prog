package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.requests.RemoveByIDRequest;
import general.network.responses.RemoveByIDResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;

/**
 * Command class that deletes an object by its ID, if one exists in the collection
 */
public class RemoveByIDCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        Integer id = ((RemoveByIDRequest) request).getId();
        if (Ticket.checkIDInUsed(id)) {
            collectionManager.removeByID(id);
            return new RemoveByIDResponse(true, true);
        } else {
            return new RemoveByIDResponse(true, false);
        }
    }

    @Override
    public String getName() {
        return "remove by id";
    }

    @Override
    public String getArgs() {
        return "id";
    }

    @Override
    public String getDescription() {
        return "Удалить элемент из коллекции по его id";
    }

    public RemoveByIDCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

package server.commands.baseCommandsServer;

import general.models.Ticket;
import general.network.Request;
import general.network.Response;
import general.network.requests.UpdateRequest;
import general.network.responses.UpdateResponse;
import general.validators.exceptions.EmptyCollectionException;
import general.validators.exceptions.IDNotFoundException;
import server.collectionManager.CollectionManager;
import server.commands.Executable;


/**
 * Command class that updates object information by its ID
 */
public class UpdateCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        Integer id = ((UpdateRequest) request).getId();
        Ticket elementForUpdating;
        try {
            elementForUpdating = collectionManager.getElementByID(id);
        } catch (IDNotFoundException | EmptyCollectionException e) {
            return new UpdateResponse(false);
        }
        Ticket newElement = ((UpdateRequest) request).getTicket();
        elementForUpdating.setName(newElement.getName());
        elementForUpdating.setComment(newElement.getComment());
        elementForUpdating.setCoordinates(newElement.getCoordinates());
        elementForUpdating.setDiscount(newElement.getDiscount());
        elementForUpdating.setEvent(newElement.getEvent());
        elementForUpdating.setPrice(newElement.getPrice());
        elementForUpdating.setType(newElement.getType());

        return new UpdateResponse(true);

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getArgs() {
        return "id {element}";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

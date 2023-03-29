package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.requests.UpdateRequest;
import general.network.responses.UpdateResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;
import general.validators.exceptions.EmptyCollectionException;
import general.validators.exceptions.IDNotFoundException;

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
        } catch (IDNotFoundException e) {
            System.out.println("Введенный ID отсутствует в коллекции");
            return new UpdateResponse(false);
        } catch (EmptyCollectionException e) {
            System.out.println("В данный момент коллекция пуста");
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

        System.out.println("Элемент успешно обновлен");
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

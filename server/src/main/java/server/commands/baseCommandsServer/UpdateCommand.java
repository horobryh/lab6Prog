package server.commands.baseCommandsServer;

import general.models.Ticket;
import general.models.User;
import general.network.Request;
import general.network.Response;
import general.network.requests.UpdateRequest;
import general.network.responses.UpdateResponse;
import general.validators.exceptions.EmptyCollectionException;
import general.validators.exceptions.IDNotFoundException;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import server.network.Server;

import java.sql.SQLException;
import java.util.Objects;


/**
 * Command class that updates object information by its ID
 */
public class UpdateCommand implements Executable {
    private final CollectionManager collectionManager;
    private Server server;
    @Override
    public Response execute(Request request) {

        Integer id = ((UpdateRequest) request).getId();
        Ticket elementForUpdating;
        try {
            elementForUpdating = collectionManager.getElementByID(id);
        } catch (EmptyCollectionException e) {
            return new UpdateResponse(false, "Произошла ошибка " + e);

        }
        User user = request.getUser();
        if (!Objects.equals(user.getId(), elementForUpdating.getCreationUser().getId())) {
            return new UpdateResponse(false, "Вы не являетесь создателем данного объекта.");
        }

        Ticket newElement = ((UpdateRequest) request).getTicket();
        newElement.setId(elementForUpdating.getId());
        newElement.getEvent().setId(elementForUpdating.getEvent().getId());
        int[] resultSet;
        try {
            resultSet = server.getDataBaseManager().updateTicket(newElement);
        } catch (SQLException e) {
            return new UpdateResponse(false, "Произошла ошибка " + e);
        }
        if (resultSet[0] < 0) {
            return new UpdateResponse(false, "Произошла ошибка обновления события");
        } else if (resultSet[1] < 0) {
            return new UpdateResponse(false, "Произошла ошибка обновления билета");
        }

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

    public UpdateCommand(CollectionManager collectionManager, Server server) {
        this.collectionManager = collectionManager;
        this.server = server;
    }
}

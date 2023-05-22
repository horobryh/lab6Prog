package server.commands.baseCommandsServer;

import general.models.User;
import general.network.Request;
import general.network.Response;
import general.network.requests.RemoveByIDRequest;
import general.network.requests.UpdateRequest;
import general.network.responses.RemoveByIDResponse;
import general.validators.exceptions.EmptyCollectionException;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;
import server.network.Server;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Command class that deletes an object by its ID, if one exists in the collection
 */
public class RemoveByIDCommand implements Executable {
    private final CollectionManager collectionManager;
    private Server server;
    @Override
    public Response execute(Request request) {
        Integer id = ((RemoveByIDRequest) request).getId();
        Ticket elementForUpdating;
        try {
            elementForUpdating = collectionManager.getElementByID(id);
        } catch (EmptyCollectionException e) {
            return new RemoveByIDResponse(false, false, "Произошла ошибка " + e);

        }
        User user = request.getUser();
        if (!Objects.equals(user.getId(), elementForUpdating.getCreationUser().getId())) {
            return new RemoveByIDResponse(false, false, "Вы не являетесь создателем данного объекта.");
        }

        try {
            if (server.getDataBaseManager().removeByID(id) > 0) {
                collectionManager.removeByID(id);
                return new RemoveByIDResponse(true, true);
            } else {
                return new RemoveByIDResponse(true, false, "Произошла ошибка при удалении объекта");
            }
        } catch (SQLException e) {
            return new RemoveByIDResponse(false, false, "Произошла ошибка при удалении объекта " + e);
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

    public RemoveByIDCommand(CollectionManager collectionManager, Server server) {
        this.collectionManager = collectionManager;
        this.server = server;
    }
}

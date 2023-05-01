package server.commands.baseCommandsServer;

import general.models.User;
import general.network.Request;
import general.network.Response;
import general.network.requests.CheckIDInCollectionRequest;
import general.network.responses.CheckIDInCollectionResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import server.network.Server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckIDInCollectionCommand implements Executable {
    private Server server;
    @Override
    public Response execute(Request request) {
        Integer id = ((CheckIDInCollectionRequest) request).getId();
        try {
            ResultSet resultSet = server.getDataBaseManager().findElementByID(id);
            User user = request.getUser();

            if (resultSet.next()) {
                if (user.getId() == resultSet.getInt("creationUserID")) {
                    return new CheckIDInCollectionResponse(true);
                } else {
                    return new CheckIDInCollectionResponse(false, "Вы не являетесь владельцем этого объекта");
                }
            } else {
                return new CheckIDInCollectionResponse(false, "Объекта с таким ID в коллекции не найдено.");
            }
        } catch (SQLException e) {
            return new CheckIDInCollectionResponse(false, "Произошла ошибка " + e);
        }
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

    public CheckIDInCollectionCommand(Server server) {
        this.server = server;
    }
}

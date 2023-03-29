package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.ShowResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;

/**
 * Command class that prints the elements of a collection
 */
public class ShowCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        return new ShowResponse(true, collectionManager.getCollection());
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

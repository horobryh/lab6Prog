package server.commands.baseCommandsServer;

import general.models.Ticket;
import general.models.comparators.TicketIDComparator;
import general.network.Request;
import general.network.Response;
import general.network.responses.ShowResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;

import java.util.List;

/**
 * Command class that prints the elements of a collection
 */
public class ShowCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        List<Ticket> collection = collectionManager.getCollection();
        collection.sort(new TicketIDComparator());
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

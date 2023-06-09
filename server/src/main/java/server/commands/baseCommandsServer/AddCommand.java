package server.commands.baseCommandsServer;

import general.models.Ticket;
import general.network.Request;
import general.network.Response;
import general.network.requests.AddRequest;
import general.network.responses.AddResponse;
import server.commands.Executable;
import server.collectionManager.CollectionManager;

/**
 * Command class that adds an element to a collection
 */
public class AddCommand implements Executable {
    private final CollectionManager collectionManager;


    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Ticket ticket = ((AddRequest) request).getTicket();
        ticket.setId(ticket.getNextID());
        this.collectionManager.add(((AddRequest) request).getTicket());
        return new AddResponse(true);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getArgs() {
        return "{element}";
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }
}

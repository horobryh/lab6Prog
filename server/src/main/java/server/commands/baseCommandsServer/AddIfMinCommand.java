package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.requests.AddIfMinRequest;
import general.network.responses.AddIfMinResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;
import general.models.comparators.TicketNameComparator;
import general.validators.exceptions.EmptyCollectionException;

/**
 * Command class that adds an element to the collection if it is less than the minimum
 */
public class AddIfMinCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        Ticket element;
        try {
            element = collectionManager.getMinElement();
        } catch (EmptyCollectionException e) {
            return new AddIfMinResponse(false, false);
        }

        Ticket newTicket = ((AddIfMinRequest) request).getTicket();
        newTicket.setId(newTicket.getNextID());
        if (new TicketNameComparator().compare(element, newTicket) < 0) {
            collectionManager.add(newTicket);
            return new AddIfMinResponse(true, true);
        } else {
            return new AddIfMinResponse(true, false);
        }
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getArgs() {
        return "{element}";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

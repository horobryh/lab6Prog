package server.commands.baseCommandsServer;

import general.models.Event;
import general.models.Ticket;
import general.network.Request;
import general.network.Response;
import general.network.requests.AddRequest;
import general.network.responses.AddResponse;
import server.commands.Executable;
import server.collectionManager.CollectionManager;
import server.network.Server;

import java.sql.SQLException;

/**
 * Command class that adds an element to a collection
 */
public class AddCommand implements Executable {
    private final CollectionManager collectionManager;
    private Server server;


    public AddCommand(CollectionManager collectionManager, Server server) {
        this.collectionManager = collectionManager;
        this.server = server;
    }

    @Override
    public Response execute(Request request) {
        Ticket ticket = ((AddRequest) request).getTicket();
        ticket.setId(ticket.getNextID());
        try {
            Event event = server.getDataBaseManager().addEvent(ticket.getEvent());
            ticket = server.getDataBaseManager().addTicket(ticket, event);
        } catch (SQLException e) {
            return new AddResponse(false, "Произошла ошибка " + e);
        }
        this.collectionManager.add(ticket);
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

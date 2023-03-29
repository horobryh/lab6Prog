package client.commands.baseCommandsClient;

import client.builders.TicketBuilder;
import general.network.requests.AddRequest;
import general.network.responses.AddResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;
import general.models.Ticket;

import java.util.Scanner;

/**
 * Command class that adds an element to a collection
 */
public class AddCommand implements Executable {
    private final ServerManager serverManager;
    public AddCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, Scanner... scanner) {
        Ticket object = new TicketBuilder(scanner[0]).buildObject();
        AddRequest request = new AddRequest(object);
        AddResponse response = (AddResponse) serverManager.sendRequestGetResponse(request, true);
        if (response.getResult()) {
            System.out.println("Добавление прошло успешно.");
        } else {
            System.out.println("Произошла ошибка при удалении объекта.");
        }
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

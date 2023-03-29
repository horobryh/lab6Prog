package client.commands.baseCommandsClient;

import client.builders.TicketBuilder;
import general.network.requests.AddIfMinRequest;
import general.network.responses.AddIfMinResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;
import general.models.Ticket;

import java.util.Scanner;

/**
 * Command class that adds an element to the collection if it is less than the minimum
 */
public class AddIfMinCommand implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        Ticket newTicket = new TicketBuilder(scanners[0]).buildObject();
        AddIfMinRequest request = new AddIfMinRequest(newTicket);
        AddIfMinResponse response = (AddIfMinResponse) serverManager.sendRequestGetResponse(request, true);

        if (response.getResult()) {
            if (response.getResultChecking()) {
                System.out.println("Добавление прошло успешно.");
            } else {
                System.out.println("Произошла ошибка - элемент не меньше минимального.");
            }
        } else {
            System.out.println("Произошла ошибка при добавлении объекта.");
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

    public AddIfMinCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

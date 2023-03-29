package client.commands.baseCommandsClient;

import client.builders.TicketBuilder;
import general.network.requests.UpdateRequest;
import general.network.responses.UpdateResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;
import general.models.Ticket;

import java.util.Scanner;

/**
 * Command class that updates object information by its ID
 */
public class UpdateCommand implements Executable {
    private final ServerManager serverManager;

    @Override
    public void execute(String[] args, Scanner... scanners) {
        Integer id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Введенный аргумент не может быть представлен в качестве id");
            return;
        }
        Ticket newElement = new TicketBuilder(scanners[0]).buildObject();
        UpdateRequest request = new UpdateRequest(id, newElement);
        UpdateResponse response = (UpdateResponse) serverManager.sendRequestGetResponse(request, true);
        if (response.getResult()) {
            System.out.println("Элемент успешно обновлен");
        } else {
            System.out.println("Произошла ошибка при изменении объекта");
        }

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

    public UpdateCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

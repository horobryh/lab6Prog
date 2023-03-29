package client.commands.baseCommandsClient;

import general.network.requests.ShowRequest;
import general.network.responses.ShowResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;
import general.models.Ticket;

import java.util.Scanner;

/**
 * Command class that prints the elements of a collection
 */
public class ShowCommand implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanner) {
        ShowRequest request = new ShowRequest();
        ShowResponse response = (ShowResponse) serverManager.sendRequestGetResponse(request, true);

        if (response.getResult()) {
            System.out.println("Элементы коллекции:");
            int i = 1;
            for (Ticket element : response.getResultList()) {
                System.out.println(i++ + ". " + element.beautifulString());
            }
        }

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

    public ShowCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

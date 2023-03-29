package client.commands.baseCommandsClient;

import general.network.requests.SortRequest;
import general.network.responses.SortResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;
import general.models.Ticket;

import java.util.List;
import java.util.Scanner;

/**
 * Command class that sorts a collection
 */
public class SortCommand implements Executable {
    private final ServerManager serverManager;

    @Override
    public void execute(String[] args, Scanner... scanners) {
        SortRequest request = new SortRequest();
        SortResponse response = (SortResponse) serverManager.sendRequestGetResponse(request, true);
        List<Ticket> collection = response.getResultList();
        System.out.println(collection);
    }

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в естественном порядке";
    }

    public SortCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

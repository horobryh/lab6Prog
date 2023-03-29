package client.commands.baseCommandsClient;

import general.network.requests.ShuffleRequest;
import general.network.responses.ShuffleResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;

import java.util.Scanner;

/**
 * Command class that randomly shuffles a collection
 */
public class ShuffleCommand implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        ShuffleRequest request = new ShuffleRequest();
        ShuffleResponse response = (ShuffleResponse) serverManager.sendRequestGetResponse(request, true);
        if (response.getResult()) {
            System.out.println("Коллекция перемешана.");
        } else {
            System.out.println("Произошла ошибка при перемешивании коллекции.");
        }
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }

    public ShuffleCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

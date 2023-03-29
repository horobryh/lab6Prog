package client.commands.baseCommandsClient;

import general.network.requests.ClearRequest;
import general.network.responses.ClearResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;

import java.util.Scanner;

/**
 * Collection cleanup command class
 */
public class ClearCommand implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        ClearRequest request = new ClearRequest();
        ClearResponse response = (ClearResponse) serverManager.sendRequestGetResponse(request, true);
        if (response.getResult()) {
            System.out.println("Очистка прошла успешно.");
        } else {
            System.out.println("Произошла ошибка при очистке коллекции.");
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    public ClearCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

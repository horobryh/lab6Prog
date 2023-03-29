package client.commands.baseCommandsClient;

import general.network.requests.InfoRequest;
import general.network.responses.InfoResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;
import general.models.Ticket;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Command class for printing full information about an existing collection
 */
public class InfoCommand implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        InfoRequest request = new InfoRequest();
        InfoResponse response = (InfoResponse) serverManager.sendRequestGetResponse(request, true);
        List<Ticket> collection = response.getResultList();
        Date initializationDate = response.getInitializationDate();
        System.out.println("Коллекция типа Vector<Ticket>");
        System.out.println("Дата инициализации: " + initializationDate);
        System.out.println("Количество элементов: " + collection.size());
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции";
    }

    public InfoCommand (ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

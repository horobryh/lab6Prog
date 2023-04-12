package client.commands.baseCommandsClient;

import client.utils.BeautfilListPrint;
import general.network.requests.PrintUniquePriceRequest;
import general.network.responses.PrintUniquePriceResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;

import java.util.List;
import java.util.Scanner;

/**
 * Command class that prints all unique values of the price field
 */
public class PrintUniquePrice implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        PrintUniquePriceRequest request = new PrintUniquePriceRequest();
        PrintUniquePriceResponse response = (PrintUniquePriceResponse) serverManager.sendRequestGetResponse(request, true);
        List<Integer> prices = response.getResultList();
        System.out.println(new BeautfilListPrint<>(prices).getBeautifulPrint());
    }

    @Override
    public String getName() {
        return "print_unique_price";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля price всех элементов в коллекции";
    }

    public PrintUniquePrice(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

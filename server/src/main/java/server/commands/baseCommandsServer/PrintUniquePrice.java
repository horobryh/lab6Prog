package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.PrintUniquePriceResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;

import java.util.List;
import java.util.Scanner;

/**
 * Command class that prints all unique values of the price field
 */
public class PrintUniquePrice implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        List<Integer> prices = collectionManager.getCollection().stream().map(Ticket::getPrice).distinct().sorted().toList();
        return new PrintUniquePriceResponse(true, prices);
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

    public PrintUniquePrice(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

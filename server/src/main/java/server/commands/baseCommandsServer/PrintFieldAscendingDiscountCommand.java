package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.PrintFieldAscendingDiscountResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;

import java.util.List;

/**
 * Command class that prints all discount values in ascending order
 */
public class PrintFieldAscendingDiscountCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        List<Long> discounts = collectionManager.getCollection().stream().map(Ticket::getDiscount).sorted().toList();
        return new PrintFieldAscendingDiscountResponse(true, discounts);
    }

    @Override
    public String getName() {
        return "print_field_ascending_discount";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля discount всех элементов в порядке возрастания";
    }

    public PrintFieldAscendingDiscountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

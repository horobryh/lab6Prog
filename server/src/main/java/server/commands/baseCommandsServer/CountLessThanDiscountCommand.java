package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.requests.CountLessThanDiscountRequest;
import general.network.responses.CountLessThanDiscountResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;

import java.util.List;

/**
 * Command class for counting elements whose discount value is less than the specified one
 */
public class CountLessThanDiscountCommand implements Executable {
    private final CollectionManager collectionManager;

    @Override
    public Response execute(Request request) {
        List<Long> discounts = collectionManager.getCollection().stream().map(Ticket::getDiscount).toList();
        Long discount = ((CountLessThanDiscountRequest) request).getDiscount();
        Long result = discounts.stream().filter(x -> x < discount).count();
        return new CountLessThanDiscountResponse(true, result);
    }

    @Override
    public String getName() {
        return "count_less_than_discount";
    }

    @Override
    public String getArgs() {
        return "discount";
    }

    @Override
    public String getDescription() {
        return " вывести количество элементов, значение поля discount которых меньше заданного";
    }

    public CountLessThanDiscountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

package client.commands.baseCommandsClient;

import general.network.requests.CountLessThanDiscountRequest;
import general.network.responses.CountLessThanDiscountResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;

import java.util.Scanner;

/**
 * Command class for counting elements whose discount value is less than the specified one
 */
public class CountLessThanDiscountCommand implements Executable {
    private final ServerManager serverManager;

    @Override
    public void execute(String[] args, Scanner... scanners) {
        Long discount;
        try {
            discount = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Введенный аргумент не содержит числа");
            return;
        }
        CountLessThanDiscountRequest request = new CountLessThanDiscountRequest(discount);
        CountLessThanDiscountResponse response = (CountLessThanDiscountResponse) serverManager.sendRequestGetResponse(request, true);

        if (response.getResult()) {
            System.out.println("Результат: " + response.getCount());
        } else {
            System.out.println("Произошла ошибка при получении результата команды.");
        }
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

    public CountLessThanDiscountCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

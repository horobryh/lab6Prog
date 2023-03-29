package client.commands.baseCommandsClient;

import general.network.requests.PrintFieldAscendingDiscountRequest;
import general.network.responses.PrintFieldAscendingDiscountResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;

import java.util.List;
import java.util.Scanner;

/**
 * Command class that prints all discount values in ascending order
 */
public class PrintFieldAscendingDiscountCommand implements Executable {
    private final ServerManager serverManager;

    @Override
    public void execute(String[] args, Scanner... scanners) {
        PrintFieldAscendingDiscountRequest request = new PrintFieldAscendingDiscountRequest();
        PrintFieldAscendingDiscountResponse response = (PrintFieldAscendingDiscountResponse) serverManager.sendRequestGetResponse(request, true);
        List<Long> discounts = response.getResultList();
        System.out.println(discounts);
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

    public PrintFieldAscendingDiscountCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

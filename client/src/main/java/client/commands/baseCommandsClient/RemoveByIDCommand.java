package client.commands.baseCommandsClient;

import general.network.requests.RemoveByIDRequest;
import general.network.responses.RemoveByIDResponse;
import client.serverManager.ServerManager;
import client.commands.Executable;

import java.util.Scanner;

/**
 * Command class that deletes an object by its ID, if one exists in the collection
 */
public class RemoveByIDCommand implements Executable {
    private final ServerManager serverManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        Integer id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Введенный аргумент не является числом");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("В аргументах команды не найден ID");
            return;
        }
        RemoveByIDRequest request = new RemoveByIDRequest(id);
        RemoveByIDResponse response = (RemoveByIDResponse) serverManager.sendRequestGetResponse(request, true);

        if (response.getResult()) {
            if (response.getResultChecking()) {
                System.out.println("Удаление прошло успешно.");
            } else {
                System.out.println("Произошла ошибка - ID не существует.");
            }
        } else {
            System.out.println("Произошла ошибка при выполнении команды.");
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getArgs() {
        return "id";
    }

    @Override
    public String getDescription() {
        return "Удалить элемент из коллекции по его id";
    }

    public RemoveByIDCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}

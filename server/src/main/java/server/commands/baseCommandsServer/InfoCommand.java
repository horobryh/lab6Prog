package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import general.network.responses.InfoResponse;
import server.collectionManager.CollectionManager;
import server.commands.Executable;
import general.models.Ticket;

import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

/**
 * Command class for printing full information about an existing collection
 */
public class InfoCommand implements Executable {
    private final CollectionManager collectionManager;
    @Override
    public Response execute(Request request) {
        Vector<Ticket> collectionElements = collectionManager.getCollection();
        Date initializationDate = collectionManager.getInitializationDate();
        System.out.println("Коллекция типа Vector<Ticket>");
        System.out.println("Дата инициализации: " + initializationDate);
        System.out.println("Количество элементов: " + collectionElements.size());
        return new InfoResponse(true, collectionElements.stream().toList(), initializationDate);
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

    public InfoCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}

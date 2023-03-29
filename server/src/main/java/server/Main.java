package server;

import general.validators.exceptions.NullException;
import server.builders.FirstStartingBuilderFromFile;
import server.collectionManager.CollectionManager;
import server.commands.CommandManager;
import server.commands.CommandRegister;

import java.io.File;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        FirstStartingBuilderFromFile builder = null;
        String filename = "output.csv";
        try {
            builder = FirstStartingBuilderFromFile.getInstance(args[0]);
            filename = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Имя файла не передано В дальнейшем возможны проблемы с сохранением данных");
        }
        File file = new File(filename);

        CollectionManager collectionManager;
        try {
            collectionManager = builder.buildObject();
            System.out.println("Данные из файла успешно загружены.");
        } catch (NullException | NullPointerException e) {
            collectionManager = CollectionManager.getInstance();
        }
        CommandManager commandManager = CommandManager.getInstance();
        CommandRegister commandRegister = CommandRegister.getInstance();

        commandRegister.registerCommands(commandManager, collectionManager, file);


    }
}
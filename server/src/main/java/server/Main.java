package server;

import general.validators.exceptions.NullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.builders.FirstStartingBuilderFromFile;
import server.collectionManager.CollectionManager;
import server.commands.CommandManager;
import server.commands.CommandRegister;
import server.network.RequestManager;
import server.network.Server;

import java.io.File;

public class Main {
    private static final Logger logger = LogManager.getLogger(Server.class);
    public static void main(String[] args) {
        FirstStartingBuilderFromFile builder = null;
        String filename = "output.csv";
        try {
            builder = FirstStartingBuilderFromFile.getInstance(args[0]);
            filename = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("Имя файла не передано В дальнейшем возможны проблемы с сохранением данных");
        }
        File file = new File(filename);

        CollectionManager collectionManager;
        try {
            assert builder != null;
            collectionManager = builder.buildObject();
            logger.info("Данные из файла успешно загружены.");
        } catch (NullException | NullPointerException e) {
            collectionManager = CollectionManager.getInstance();
        }
        RequestManager requestManager = RequestManager.getInstance();
        CommandManager commandManager = CommandManager.getInstance(requestManager);
        Server server = new Server(10101, commandManager, logger);
        CommandRegister commandRegister = CommandRegister.getInstance();

        commandRegister.registerCommands(commandManager, collectionManager, file);

        server.run();

    }
}
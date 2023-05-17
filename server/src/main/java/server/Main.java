package server;

import general.validators.exceptions.NullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.builders.FirstStartBuilderFromDB;
import server.collectionManager.CollectionManager;
import server.commands.CommandManager;
import server.commands.CommandRegister;
import server.network.RequestManager;
import server.network.Server;

import javax.naming.NamingException;
import java.io.*;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Server.class);
    public static void main(String[] args) {
        CollectionManager collectionManager;
        collectionManager = CollectionManager.getInstance();
        RequestManager requestManager = RequestManager.getInstance();
        CommandManager commandManager = CommandManager.getInstance(requestManager);

        String login = "", password = "";

        try (FileReader fileReader = new FileReader("credentials.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            login = bufferedReader.readLine();
            password = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            logger.error("Сервер не смог найти файл credentials.txt " + e);
            exit(0);
        } catch (IOException e) {
            logger.error("Произошла ошибка чтения файла credentials.txt " + e);
            exit(0);
        }

        Server server = null;
        System.out.print("Введите начальный порт для запуска:\t");
        try {
            String startingPort = new Scanner(System.in).nextLine();
            server = new Server(Integer.parseInt(startingPort), commandManager, logger, "jdbc:postgresql://localhost:5432/studs", login, password);
        } catch (SQLException | NamingException e) {
            logger.error("Произошла ошибка соединения с базой данных " + e);
            exit(0);
        } catch (IllegalArgumentException e) {
            logger.error("Передан неверный порт " + e);
            exit(0);
        } catch (NoSuchElementException e) {
            logger.error("Передан символ завершения ввода " + e);
            exit(0);
        }

        FirstStartBuilderFromDB builderFromDB;
        try {
            builderFromDB = FirstStartBuilderFromDB.getInstance(server.getDataBaseManager());
            collectionManager = builderFromDB.buildObject();
        } catch (SQLException e) {
            logger.error("Произошла ошибка загрузки коллекции из базы данных " + e);
            exit(0);
        } catch (NullException e) {
            logger.error("Произошла ошибка " + e);
            exit(0);
        }
        logger.info("Коллекция загружена успешно.");
        CommandRegister commandRegister = CommandRegister.getInstance();

        commandRegister.registerCommands(commandManager, collectionManager, server);

        server.run();

    }
}
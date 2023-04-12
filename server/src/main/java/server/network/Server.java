package server.network;

import general.network.Request;
import general.network.Response;
import server.commands.CommandManager;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

public class Server {
    private final Logger logger;
    private Integer port;
    private ServerSocket serverSocket;
    private final CommandManager commandManager;
    private final Scanner scanner;

    public Server(Integer port, CommandManager commandManager, Logger logger) {
        this.port = port;
        this.commandManager = commandManager;
        this.logger = logger;
        this.scanner = new Scanner(new BufferedInputStream(System.in));
        this.connect();
    }

    private void connect() {
        while (true) {
            try {
                this.serverSocket = new ServerSocket(this.port);
                this.serverSocket.setSoTimeout(1000);
                logger.info("Сервер запущен на порте " + port);
                return;
            } catch (BindException e) {
                logger.error("Произошла ошибка, пробую порт " + ++port);
            } catch (IOException e) {
                logger.error("Произошла ошибка " + e);
            }
        }
    }

    public void run() {
        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {

                InputStream inputStream = clientSocket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);

                logger.info("\n>>> Новое подключение");

                Request request = (Request) ois.readObject();
                logger.info("Получен новый запрос " + request);
                Response response = new Response(false) {
                };
                try {
                    response = commandManager.executeCommand(request);
                } catch (IllegalArgumentException e) {
                    logger.error(e);
                    continue;
                }
                ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());
                ous.writeObject(response);
                logger.info("Ответ " + response + " отправлен");
            } catch (EOFException ignored) {
            } catch (SocketTimeoutException e) {
                try {
                    String command;
                    if (System.in.available() > 0 && scanner.hasNextLine()) {
                        command = scanner.nextLine();
                    } else {
                        continue;
                    }
                    if (command.equals("save")) {
                        commandManager.getCommands().get("save").execute(new Request() {
                        });
                    } else {
                        logger.info("Неизвестная команда.");
                    }
                } catch (IOException ignored) {
                }
            } catch (IOException e) {
                logger.error("Произошла ошибка получения ServerSocket " + e);
            } catch (ClassNotFoundException e) {
                logger.error("Произошла ошибка десериализации полученного объекта");
            }
        }
    }
}

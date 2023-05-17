package server.network;

import general.network.Request;
import general.network.Response;
import server.commands.CommandManager;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.Logger;
import server.database.DataBaseManager;

import javax.naming.NamingException;

public class Server {
    private final Logger logger;
    private Integer port;
    private ServerSocket serverSocket;
    private final CommandManager commandManager;
    private final Scanner scanner;
    private DataBaseManager dataBaseManager;

    public Server(Integer port, CommandManager commandManager, Logger logger, String dataBaseUrl, String dataBaselogin, String dataBasePassword) throws SQLException, NamingException {
        this.port = port;
        this.commandManager = commandManager;
        this.logger = logger;
        this.scanner = new Scanner(new BufferedInputStream(System.in));
        this.connect();
        dataBaseManager = new DataBaseManager(dataBaseUrl, dataBaselogin, dataBasePassword);
        logger.info("Соединение с базой данных прошло успешно.");
    }

    private void connect() {
        while (true) {
            try {
                this.serverSocket = new ServerSocket(this.port);
//                this.serverSocket.setSoTimeout(1000);
                logger.info("Сервер запущен на порте " + port);
                return;
            } catch (BindException e) {
                logger.error("Произошла ошибка, пробую порт " + ++port);
            } catch (IOException e) {
                logger.error("Произошла ошибка " + e);
            }
        }
    }

    public Socket getNewConnection() throws IOException {
        return serverSocket.accept();
    }

    public Request readRequest(Socket clientSocket) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request clientRequest = (Request) objectInputStream.readObject();
            logger.info(">>> Запрос " + clientRequest + " получен");

            return clientRequest;
        } catch (EOFException ignored) {
        } catch (IOException e) {
            logger.error("Произошла ошибка получения объекта " + e);
        } catch (
                ClassNotFoundException e) {
            logger.error("Произошла ошибка десериализации полученного объекта " + e);
        }
        return null;
    }

    public Response commandProcessing(Request clientRequest) {
        Response response = new Response(false) {
        };
        try {
            response = commandManager.executeCommand(clientRequest);
        } catch (IllegalArgumentException e) {
            logger.error(e);
        }
        return response;
    }

    public void sendResponse(Socket clientSocket, Response serverResponse) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(serverResponse);
            clientSocket.close();
            logger.info("<<< Ответ " + serverResponse + " отправлен");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void run() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
            try {
                Socket clientSocket = getNewConnection();
                // Чтение запроса
                if (clientSocket == null) continue;
                FutureTask<Request> socketFuture = new FutureTask<>(() -> readRequest(clientSocket));
                Thread thread = new Thread(socketFuture);
                thread.start();
                Request clientRequest = socketFuture.get();
                thread.interrupt();

                // Выполнение запроса
                if (clientRequest == null) continue;
                RecursiveTask<Response> responseRecursiveTask = new RecursiveTask<>() {
                    @Override
                    protected Response compute() {
                        return commandProcessing(clientRequest);
                    }
                };
                Response serverResponse = forkJoinPool.invoke(responseRecursiveTask);
                responseRecursiveTask.cancel(false);

                // Отправка ответа
                if (serverResponse == null) continue;
                executorService.submit(() -> sendResponse(clientSocket, serverResponse));

//                new Thread(() -> {
//                    if (clientSocket == null) Thread.currentThread().interrupt();
//                    if (!Thread.currentThread().isInterrupted()) {
//                        Request clientRequest = readRequest(clientSocket);
//                        if (clientRequest == null) Thread.currentThread().interrupt();
//                        if (!Thread.currentThread().isInterrupted()) {
//                            RecursiveTask<Response> responseRecursiveTask = new RecursiveTask<>() {
//                                @Override
//                                protected Response compute() {
//                                    return commandProcessing(clientRequest);
//                                }
//                            };
//                            responseRecursiveTask.fork();
//                            Response serverResponse = responseRecursiveTask.join();
//                            if (serverResponse != null) {
//                                ExecutorService executorService = Executors.newFixedThreadPool(5);
//                                executorService.submit(() -> sendResponse(clientSocket, serverResponse));
//                                executorService.shutdown();
//                            }
//                            responseRecursiveTask.cancel(true);
//                        }
//                    }
//                    Thread.currentThread().interrupt();
//                }).start();
            } catch (IOException | ExecutionException | InterruptedException e) {
                logger.error(e);
            }
        }
    }

    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }
}
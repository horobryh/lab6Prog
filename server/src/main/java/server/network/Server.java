package server.network;

import general.network.Request;
import general.network.Response;
import org.w3c.dom.ls.LSOutput;
import server.commands.CommandManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Integer host;
    private ServerSocket serverSocket;
    private final CommandManager commandManager;

    public Server(Integer host, CommandManager commandManager) {
        this.host = host;
        this.commandManager = commandManager;
    }

    private void connect() {
        boolean check = false;
        while (check) {
            try (ServerSocket serverSocket = new ServerSocket()) {
                this.serverSocket = serverSocket;
            } catch (IOException e) {
                System.out.println("Произошла ошибка, пробую порт " + ++host);
            }
        }
    }

    public void run() {
        while (true) {
            try (Socket clientSocket = serverSocket.accept();) {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                Request request = (Request) ois.readObject();
                System.out.println("Получен новый запрос");

                Response response = commandManager.executeCommand(request);

                ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());
                ous.writeObject(response);
                System.out.println("Ответ на запрос отправлен.");
            } catch (IOException e) {
                System.out.println("Произошла ошибка получения ServerSocket");
            } catch (ClassNotFoundException e) {
                System.out.println("Произошла ошибка десериализации полученного объекта");
                ;
            }

        }
    }
}

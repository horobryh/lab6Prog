package client.builders;

import client.commands.CommandManager;
import client.commands.CommandRegister;
import client.commands.baseCommandsClient.AuthCommand;
import client.serverManager.ServerManager;
import general.models.User;
import general.validators.exceptions.AuthorizationException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class FirstStartBuilder {
    private ServerManager serverManager;
    private Scanner scanner;
    private Integer port;
    private CommandManager commandManager;
    private CommandRegister commandRegister;

    public void setPort(Integer port) throws NullPointerException, IOException, IllegalArgumentException {

        if (port == null) throw new NullPointerException("Передан null");
        serverManager = ServerManager.getInstance(InetAddress.getLocalHost(), port);
        Socket socket = new Socket(InetAddress.getLocalHost(), port);
        socket.close();
        System.out.println("Сервер подключен.");

        this.commandManager = CommandManager.getInstance();
        this.commandRegister = CommandRegister.getInstance();

        commandRegister.registerCommands(commandManager, serverManager);
    }

    public String authorization(String login, String password) throws AuthorizationException {
        ArrayList arrayList = new AuthCommand(serverManager).getUser(login, password);
        User user = (User) arrayList.get(0);
        String message = arrayList.get(1).equals("") ? ((Boolean) arrayList.get(2) ? "Успешно" : "Произошла ошибка") : (String) arrayList.get(1);
        Boolean result = (Boolean) arrayList.get(2);
        if (!result) {
            throw new AuthorizationException(message);
        } else {
            serverManager.addUser(user);
            return message;
        }
    }

    public FirstStartBuilder() {
        this.serverManager = null;
        this.scanner = new Scanner(System.in);
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
}

package client;

import client.commands.CommandManager;
import client.commands.CommandRegister;
import client.commands.baseCommandsClient.AuthCommand;
import client.scannerManager.ScannerManager;
import client.serverManager.ServerManager;
import general.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServerManager serverManager = null;
        Scanner scanner = new Scanner(System.in);
        String obj = null;
        Integer port;
        do {
            System.out.print("Введите порт: ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }

            try {
                port = Integer.parseInt(obj);
                break;
            } catch (NumberFormatException e) {
                System.out.println(e + "Введенный аргумент не число.");
            }

        } while (true);
        try {
            serverManager = ServerManager.getInstance(InetAddress.getLocalHost(), port);
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            socket.close();
            System.out.println("Сервер подключен.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Произошла ошибка подключения ServerManager " + e);
            System.out.println("Завершение программы...");
            System.exit(0);
        }
        CommandManager commandManager = CommandManager.getInstance();
        CommandRegister commandRegister = CommandRegister.getInstance();

        commandRegister.registerCommands(commandManager, serverManager);

        User user = authorization(serverManager);

        serverManager.addUser(user);

        InputStream inputStream = System.in;

        ScannerManager scannerManager = new ScannerManager(inputStream, commandManager, true);
        try {
            scannerManager.startScan();
        } catch (NullPointerException e) {
            System.out.println("Произошла ошибка, сервер недоступен " + e);
            commandManager.getCommands().get("exit").execute(new String[1]);
        }
    }

    private static User authorization(ServerManager serverManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Авторизация. Если вы еще не зарегистрированы в системе, придумайте ваши логин и пароль.");
        System.out.print("Введите логин:\t");
        String login = scanner.nextLine();
        System.out.print("Введите пароль:\t");
        String password = scanner.nextLine();

        return new AuthCommand(serverManager).getUser(login, password);
    }
}
package client;

import client.commands.CommandManager;
import client.commands.CommandRegister;
import client.scannerManager.ScannerManager;
import client.serverManager.ServerManager;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        CommandManager commandManager = CommandManager.getInstance();
        CommandRegister commandRegister = CommandRegister.getInstance();
        ServerManager serverManager = ServerManager.getInstance(); // TODO: добавить ввод порта
        commandRegister.registerCommands(commandManager, serverManager);

        InputStream inputStream = System.in;

        ScannerManager scannerManager = new ScannerManager(inputStream, commandManager, true);
        scannerManager.startScan();
    }
}
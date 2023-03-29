package client.commands.baseCommandsClient;

import client.commands.CommandManager;
import client.commands.Executable;
import client.scannerManager.ScannerManager;
import client.scannerManager.StackManager;

import java.io.*;
import java.util.Scanner;

/**
 * Command class for launching a script from a file
 */
public class ExecuteScriptCommand implements Executable {
    private final CommandManager commandManager;
    @Override
    public void execute(String[] args, Scanner... scanners) {
        InputStream inputStream;
        if (StackManager.checkOnRecursion(new File(args[0]).getAbsolutePath())) {
            System.out.println("В файлах содержится рекурсия. Операция прервана.");
            return;
        }
        StackManager.addFilename(new File(args[0]).getAbsolutePath());
        try {
            inputStream = new BufferedInputStream(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден (Или отсутствует доступ к файлу)");
            return;
        } catch (SecurityException e) {
            System.out.println("Отсутствует доступ к файлу");
            return;
        }
        ScannerManager scannerManager = new ScannerManager(inputStream, commandManager, false);
        scannerManager.startScan();
        StackManager.pop();
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getArgs() {
        return "filename";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    public ExecuteScriptCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}

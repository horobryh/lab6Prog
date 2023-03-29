package client.commands.baseCommandsClient;

import client.commands.Executable;

import java.util.Scanner;

/**
 * Program exit command class
 */
public class ExitCommand implements Executable {
    @Override
    public void execute(String[] args, Scanner... scanners) {
        System.out.println("Выход из приложения...");
        System.exit(0);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}

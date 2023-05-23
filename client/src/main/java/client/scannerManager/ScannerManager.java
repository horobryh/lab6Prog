package client.scannerManager;

import client.commands.CommandManager;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

/**
 * Current Scanner Class
 */
public class ScannerManager {
    private Scanner scanner;
    private CommandManager commandManager;
    private boolean userMode;
    private static Stack<String> stack = new Stack<>();
    public ScannerManager(InputStream inputStream, CommandManager commandManager, boolean userMode) {
        this.commandManager = commandManager;
        this.scanner = new Scanner(inputStream);
        this.userMode = userMode;
    }

    public void startScan() {
        if (this.userMode) {
            System.out.println("Введите команду:");
        }
        while (scanner.hasNext()) {
            String[] arguments = scanner.nextLine().split(" ");
            try {
                commandManager.executeCommand(arguments, scanner);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (this.userMode) {
                System.out.println();
                System.out.println("Введите команду:");
            }
        }
    }

    public Scanner getScanner() {
        return this.scanner;
    }
}

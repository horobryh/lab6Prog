package client.commands;

import java.util.Scanner;

/**
 * Interface of commands that can be called
 */
public interface Executable {
    void execute(String[] args, Scanner... scanner);
    String getName();
    String getArgs();
    String getDescription();
}

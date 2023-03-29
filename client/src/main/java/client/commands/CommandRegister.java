package client.commands;

import client.commands.baseCommandsClient.*;
import client.serverManager.ServerManager;

import java.io.File;

/**
 * Command registration class in command manager
 */
public class CommandRegister {
    private static CommandRegister instance = null;

    private CommandRegister() {}

    public static CommandRegister getInstance() {
        if (instance == null) {
            instance = new CommandRegister();
        }
        return instance;
    }

    public void registerCommands(CommandManager commandManager, ServerManager serverManager) {
        commandManager.regNewCommand("add", new AddCommand(serverManager));
        commandManager.regNewCommand("help", new HelpCommand(commandManager));
        commandManager.regNewCommand("info", new InfoCommand(serverManager));
        commandManager.regNewCommand("remove_by_id", new RemoveByIDCommand(serverManager));
        commandManager.regNewCommand("clear", new ClearCommand(serverManager));
        commandManager.regNewCommand("exit", new ExitCommand());
        commandManager.regNewCommand("count_less_than_discount", new CountLessThanDiscountCommand(serverManager));
        commandManager.regNewCommand("shuffle", new ShuffleCommand(serverManager));
        commandManager.regNewCommand("print_unique_price", new PrintUniquePrice(serverManager));
        commandManager.regNewCommand("print_field_ascending_discount", new PrintFieldAscendingDiscountCommand(serverManager));
        commandManager.regNewCommand("add_if_min", new AddIfMinCommand(serverManager));
        commandManager.regNewCommand("update", new UpdateCommand(serverManager));
        commandManager.regNewCommand("execute_script", new ExecuteScriptCommand(commandManager));
        commandManager.regNewCommand("sort", new SortCommand(serverManager));
        commandManager.regNewCommand("show", new ShowCommand(serverManager));

    }
}

package server.commands;

import server.collectionManager.CollectionManager;
import server.commands.baseCommandsServer.*;

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

    public void registerCommands(CommandManager commandManager, CollectionManager collectionManager, File file) {
        commandManager.regNewCommand("add", new AddCommand(collectionManager));
        commandManager.regNewCommand("help", new HelpCommand(commandManager));
        commandManager.regNewCommand("info", new InfoCommand(collectionManager));
        commandManager.regNewCommand("remove_by_id", new RemoveByIDCommand(collectionManager));
        commandManager.regNewCommand("clear", new ClearCommand(collectionManager));
        commandManager.regNewCommand("save", new SaveCommand(collectionManager, file));
        commandManager.regNewCommand("exit", new ExitCommand());
        commandManager.regNewCommand("count_less_than_discount", new CountLessThanDiscountCommand(collectionManager));
        commandManager.regNewCommand("shuffle", new ShuffleCommand(collectionManager));
        commandManager.regNewCommand("print_unique_price", new PrintUniquePrice(collectionManager));
        commandManager.regNewCommand("print_field_ascending_discount", new PrintFieldAscendingDiscountCommand(collectionManager));
        commandManager.regNewCommand("add_if_min", new AddIfMinCommand(collectionManager));
        commandManager.regNewCommand("update", new UpdateCommand(collectionManager));
        commandManager.regNewCommand("sort", new SortCommand(collectionManager));
        commandManager.regNewCommand("show", new ShowCommand(collectionManager));
        commandManager.regNewCommand("__check_id_in_collection", new CheckIDInCollectionCommand(collectionManager));

    }
}

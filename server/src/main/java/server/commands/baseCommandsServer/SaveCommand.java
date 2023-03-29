package server.commands.baseCommandsServer;

import general.network.Request;
import general.network.Response;
import server.collectionManager.CollectionManager;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import server.commands.Executable;
import general.models.Ticket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
 * Command class that saves a collection to a file
 */
public class SaveCommand implements Executable {
    private final CollectionManager collectionManager;
    private File file;
    @Override
    public Response execute(Request request) {
        try {
            Writer writer = new FileWriter(file);
            StatefulBeanToCsv<Ticket> beanToCsv = new StatefulBeanToCsvBuilder<Ticket>(writer).build();
            beanToCsv.write(collectionManager.getCollection().stream().toList());
            writer.close();
            System.out.println("Сохранение прошло успешно. Путь к файлу: " + file.getAbsolutePath());
        } catch (SecurityException e) {
            System.out.println("Запрещен доступ к файлу");
        } catch (CsvDataTypeMismatchException e) {
            System.out.println("Ошибка несоответствия типа данных. CSV.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода.");
        } catch (CsvRequiredFieldEmptyException e) {
            System.out.println("Ошибка записи в формат CSV");
        }
        return null;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    public SaveCommand(CollectionManager collectionManager, File file) {
        this.collectionManager = collectionManager;
        this.file = file;
    }
}

package server.builders;

import server.collectionManager.CollectionManager;
import com.opencsv.bean.CsvToBeanBuilder;
import general.models.Ticket;
import general.validators.exceptions.NullException;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Creating an initial collection object from a file
 */
public class FirstStartingBuilderFromFile {
    private final String filename;
    private static FirstStartingBuilderFromFile instance = null;

    /**
     * @return Collection of objects created earlier
     */
    public CollectionManager buildObject() throws NullException {
        List<Ticket> beans = null;
        try (InputStream fis = new FileInputStream(filename)) {
            BufferedInputStream bis = new BufferedInputStream(fis);
            Reader rd = new BufferedReader(new InputStreamReader(bis));
            try {
                beans = new CsvToBeanBuilder<Ticket>(rd).withType(Ticket.class).build().parse();
            } catch (Exception e) {
                System.out.println("Произошла ошибка при распаковке файла с коллекцией. Операция отменена.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new CollectionManager(checkID(beans));
    }

    private FirstStartingBuilderFromFile(String filename) {
        this.filename = filename;
    }

    public static FirstStartingBuilderFromFile getInstance(String filename) {
        if (instance == null) {
            instance = new FirstStartingBuilderFromFile(filename);
        }
        return instance;
    }

    private List<Ticket> checkID(List<Ticket> collectionFromFile) {
        if (collectionFromFile==null) {
            return new Vector<>();
        }
        Set<Integer> usedID = new java.util.HashSet<>(Set.of());
        for (Ticket ticket : collectionFromFile) {
            if (usedID.contains(ticket.getId())) {
                ticket.setId(ticket.getNextID());
            }
            usedID.add(ticket.getId());
        }
        return collectionFromFile;
    }
}

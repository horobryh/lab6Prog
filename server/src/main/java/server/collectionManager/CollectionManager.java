package server.collectionManager;

import general.models.Ticket;
import general.models.comparators.TicketIDComparator;
import general.validators.exceptions.EmptyCollectionException;
import general.validators.exceptions.IDNotFoundException;
import general.validators.exceptions.NullException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Class for manipulating the main collection of objects
 */
public class CollectionManager {
    private List<Ticket> collection = Collections.synchronizedList(new Vector<>());
    private final Date initializationDate;
    private static CollectionManager instance = null;
    private CollectionManager() {
        initializationDate = new Date();
    }
    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }

    /**
     * @param element Item to add to the collection
     */
    public void add(Ticket element) {
        collection.add(element);
    }

    /**
     * @return collection
     */
    public List<Ticket> getCollection() {
        return collection;
    }

    public Date getInitializationDate() {
        return initializationDate;
    }

    /**
     * @param id Object ID to delete
     */
    public void removeByID(Integer id) {
        collection.removeIf(n -> (n.getId() == id));
        Ticket.removeInUsedID(id);
    }

    /**
     * Clear collection
     */
    public void clear() {
        this.collection.clear();
    }

    /**
     * Shuffle collection
     */
    public void shuffle() {
        Collections.shuffle(collection);
    }

    /**
     * @return The minimum element of the collection
     * @throws EmptyCollectionException Collection is empty
     */
    public Ticket getMinElement() throws EmptyCollectionException{
        if (collection.isEmpty()) {
            throw new EmptyCollectionException("Коллекция пуста, минимальных элементов нет.");
        }
        sortCollection();
        return collection.get(0);
    }

    public void sortCollection() {
        collection.sort(new TicketIDComparator());
    }

    /**
     * @param id Element ID
     * @return Element object with ID
     * @throws IDNotFoundException There is no object with this ID in the collection
     * @throws EmptyCollectionException Collection is empty
     */
    public Ticket getElementByID(Integer id) throws IDNotFoundException, EmptyCollectionException {
        if (!Ticket.checkIDInUsed(id)) {
            throw new IDNotFoundException("ID не найден в коллекции");
        }
        List<Ticket> element = collection.stream().filter(x -> x.getId() == id).toList();
        if (element.isEmpty()) {
            throw new EmptyCollectionException("Коллекция пуста.");
        }
        return element.get(0);
    }

    public CollectionManager(List<Ticket> collection) throws NullException {
        if (collection == null) {
            throw new NullException("Вместо коллекции передан null.");
        }
        this.collection = new Vector<>(collection);
        this.initializationDate = new Date();
    }

    public boolean checkIDInCollection(Integer id) {
        return collection.stream().map(Ticket::getId).toList().contains(id);
    }
}

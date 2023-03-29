package general.models;

/**
 * Interface of classes that have the function of issuing an automatic ID
 * @param <T> Object type
 */
public interface AutomaticID<T> {
    public T getNextID();
}

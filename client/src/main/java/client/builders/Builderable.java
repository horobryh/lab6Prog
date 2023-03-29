package client.builders;

/**
 * @param <T> Type of object being created
 */
public interface Builderable<T> {
    /**
     * @return Ready object of type <T>
     */
    T buildObject();
}

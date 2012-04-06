package manager.tags;

/**
 * Pojawienie się tego wyjątku oznacza że użyto metody klasy Tags która wymaga dostępu do TagFilesStore, ale nie podano
 * stosownego obiektu.
 *
 * @author Maciej Poleski
 */
public class StoreNotAvailableException extends RuntimeException {
    public StoreNotAvailableException() {
    }

    public StoreNotAvailableException(String message) {
        super(message);
    }

    public StoreNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreNotAvailableException(Throwable cause) {
        super(cause);
    }
}

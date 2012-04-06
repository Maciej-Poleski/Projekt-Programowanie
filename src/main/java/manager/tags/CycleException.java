package manager.tags;

/**
 * Pojawienie się tego wyjątku oznacza że natknięto się na cykl (lub próbę jego utworzenia).
 */
public class CycleException extends Exception {
    public CycleException() {
    }

    public CycleException(String message) {
        super(message);
    }

    public CycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CycleException(Throwable cause) {
        super(cause);
    }
}

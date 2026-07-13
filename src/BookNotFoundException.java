/**
 * Thrown when a requested ISBN does not exist in the catalog.
 */
public class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

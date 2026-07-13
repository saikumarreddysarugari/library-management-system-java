/**
 * Thrown when a requested member ID does not exist.
 */
public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
}

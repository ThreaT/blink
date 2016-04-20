package cool.blink.back.exception;

public class DuplicateDatabaseException extends Exception {

    public DuplicateDatabaseException() {

    }

    public DuplicateDatabaseException(final String message) {
        super(message);
    }

}

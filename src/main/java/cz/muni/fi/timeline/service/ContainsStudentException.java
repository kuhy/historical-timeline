package cz.muni.fi.timeline.service;

/**
 * Exception for handling adding the same student into group
 * @author Matej Macák
 */
public class ContainsStudentException extends Exception {

    public ContainsStudentException() {
        super();
    }

    public ContainsStudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContainsStudentException(String message) {
        super(message);
    }

    public ContainsStudentException(Throwable cause) {
        super(cause);
    }

}

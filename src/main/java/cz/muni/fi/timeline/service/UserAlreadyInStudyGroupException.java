package cz.muni.fi.timeline.service;

/**
 * Exception for handling adding the same student into group
 * @author Matej Macák
 */
public class UserAlreadyInStudyGroupException extends Exception {

    public UserAlreadyInStudyGroupException() {
        super();
    }

    public UserAlreadyInStudyGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyInStudyGroupException(String message) {
        super(message);
    }

    public UserAlreadyInStudyGroupException(Throwable cause) {
        super(cause);
    }

}

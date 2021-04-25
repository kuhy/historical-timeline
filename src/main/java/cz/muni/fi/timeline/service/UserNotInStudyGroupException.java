package cz.muni.fi.timeline.service;

/**
 * Exception for handling adding the same student into group
 * @author Matej Macák
 */
public class UserNotInStudyGroupException extends Exception {

    public UserNotInStudyGroupException() {
        super();
    }

    public UserNotInStudyGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotInStudyGroupException(String message) {
        super(message);
    }

    public UserNotInStudyGroupException(Throwable cause) {
        super(cause);
    }

}

package cz.muni.fi.timeline.api.exception;

/**
 * Exception for handling adding the same user into group
 * @author Matej Macák
 */
public class UserAlreadyInStudyGroupException extends Exception {

    public UserAlreadyInStudyGroupException(String msg) {
        super(msg);
    }

    public UserAlreadyInStudyGroupException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserAlreadyInStudyGroupException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyInStudyGroupException() {super(); }
}

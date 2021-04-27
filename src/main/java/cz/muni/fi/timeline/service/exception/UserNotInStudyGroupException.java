package cz.muni.fi.timeline.service.exception;

/**
 * Exception for handling a missing user in StudyGroup.
 *
 * @author Tri Le Mau
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

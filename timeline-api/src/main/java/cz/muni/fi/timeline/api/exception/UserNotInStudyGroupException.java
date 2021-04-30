package cz.muni.fi.timeline.api.exception;

/**
 * Exception for handling a missing user in StudyGroup.
 *
 * @author Tri Le Mau
 */
public class UserNotInStudyGroupException extends Exception {

    public UserNotInStudyGroupException(String msg) {
        super(msg);
    }

    public UserNotInStudyGroupException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

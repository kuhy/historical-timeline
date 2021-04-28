package cz.muni.fi.timeline.api.exception;

import org.springframework.dao.DataAccessException;

/**
 * Exception for handling a missing user in StudyGroup.
 *
 * @author Tri Le Mau
 */
public class UserNotInStudyGroupException extends DataAccessException {

    public UserNotInStudyGroupException(String msg) {
        super(msg);
    }

    public UserNotInStudyGroupException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

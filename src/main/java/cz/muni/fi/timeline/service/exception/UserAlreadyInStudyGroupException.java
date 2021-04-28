package cz.muni.fi.timeline.service.exception;

import org.springframework.dao.DataAccessException;

/**
 * Exception for handling adding the same user into group
 * @author Matej Macák
 */
public class UserAlreadyInStudyGroupException extends DataAccessException {

    public UserAlreadyInStudyGroupException(String msg) {
        super(msg);
    }

    public UserAlreadyInStudyGroupException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

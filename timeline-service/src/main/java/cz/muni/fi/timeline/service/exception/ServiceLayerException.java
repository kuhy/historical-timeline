package cz.muni.fi.timeline.service.exception;

import org.springframework.dao.DataAccessException;

/**
 * Exception used for handling Service layer
 * @author Tri Le Mau
 */
public class ServiceLayerException extends DataAccessException {

    public ServiceLayerException(String msg) {
        super(msg);
    }

    public ServiceLayerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

package cz.muni.fi.timeline.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which is thrown when attempt to modify a resource was unsuccessful.
 *
 * @author Ondřej Kuhejda
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "The requested resource was not modified")
public class ResourceNotModifiedException extends RuntimeException {

}

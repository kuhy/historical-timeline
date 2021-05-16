package cz.muni.fi.timeline.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which is thrown when attempting to create resource which already exists.
 *
 * @author Ondřej Kuhejda
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The resource already exists")
public class ResourceAlreadyExistsException extends RuntimeException {

}

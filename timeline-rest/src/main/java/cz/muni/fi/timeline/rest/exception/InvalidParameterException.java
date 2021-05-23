package cz.muni.fi.timeline.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which is thrown when invalid input is passed to the REST endpoint.
 *
 * @author Ondřej Kuhejda
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "The passed argument is invalid")
public class InvalidParameterException extends RuntimeException {

}

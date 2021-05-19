package cz.muni.fi.timeline.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "An user is already logged in.")
public class UserAlreadyLoggedInException extends RuntimeException {
}

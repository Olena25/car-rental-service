package com.intellias.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "This email is already registered")
public class EmailIsAlreadyExistException extends RuntimeException {
}

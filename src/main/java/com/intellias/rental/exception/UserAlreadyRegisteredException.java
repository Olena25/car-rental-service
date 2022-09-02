package com.intellias.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User with passport number is already registered")
public class UserAlreadyRegisteredException extends RuntimeException {
}

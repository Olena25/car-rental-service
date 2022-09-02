package com.intellias.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User has active rent")
public class UserHasActiveRentException extends RuntimeException{
}

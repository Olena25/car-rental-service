package com.intellias.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Start date cannot be after end date for rent")
public class RentDatesException extends RuntimeException {
    public RentDatesException(String message) {
        super(message);
    }
}

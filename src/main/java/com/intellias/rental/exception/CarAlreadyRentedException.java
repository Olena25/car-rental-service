package com.intellias.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Car is already rented")
public class CarAlreadyRentedException extends RuntimeException {
}

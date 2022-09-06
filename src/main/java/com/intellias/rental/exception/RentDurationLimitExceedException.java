package com.intellias.rental.exception;

public class RentDurationLimitExceedException extends RuntimeException {
    public RentDurationLimitExceedException(String message) {
        super(message);
    }
}

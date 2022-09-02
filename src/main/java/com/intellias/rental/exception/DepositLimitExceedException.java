package com.intellias.rental.exception;

public class DepositLimitExceedException extends RuntimeException {
    public DepositLimitExceedException(String message) {
        super(message);
    }
}

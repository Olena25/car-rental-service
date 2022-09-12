package com.intellias.rental.exception;


import lombok.Getter;

@Getter
public class RentIsNotFoundForUserException extends RuntimeException {

    private final String passportNumber;

    public RentIsNotFoundForUserException(String passportNumber) {
        super("Rent is not found for user ");
        this.passportNumber = passportNumber;
    }
}

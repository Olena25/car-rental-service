package com.intellias.rental.exception;

import lombok.Getter;

@Getter
public class NotEnoughMoneyForRentException extends RuntimeException {

    private final long totalRentPrice;

    public NotEnoughMoneyForRentException(long totalRentPrice) {
        super("User does not have enough money to rent car");
        this.totalRentPrice = totalRentPrice;
    }
}

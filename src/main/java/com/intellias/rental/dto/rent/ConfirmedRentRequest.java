package com.intellias.rental.dto.rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConfirmedRentRequest {
    private String passportNumber;
}

package com.intellias.rental.dto;

import com.intellias.rental.enums.CarStatus;
import com.intellias.rental.enums.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private int id;
    private String number;
    private String make;
    private String model;
    private int year;
    private int pricePerDay;
    private Group group;
    private CarStatus status;
}

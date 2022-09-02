package com.intellias.rental.model;


import com.intellias.rental.enums.CarStatus;
import com.intellias.rental.enums.Group;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "car")
public class Car {
    @Id
    private int id;
    @Column(name = "car_number")
    private String number;
    private String make;
    private String model;
    private int year;
    @Column(name = "price_per_day")
    private int pricePerDay;
    @Column(name = "car_group")
    @Enumerated(EnumType.STRING)
    private Group group;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
}

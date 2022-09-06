package com.intellias.rental.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    private String surname;
    private LocalDate dob;
    private Integer age;
    @Column(name = "passport_number")
    private String passportNumber;
    @OneToOne(mappedBy = "user")
    private Wallet wallet;

}

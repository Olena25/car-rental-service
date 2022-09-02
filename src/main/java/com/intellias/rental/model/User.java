package com.intellias.rental.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Entity(name = "user")
public class User {
    @Id
    private int id;
    @Column(name = "first_name")
    private String firstName;
    private String surname;
    private LocalDate dob;
    @Column(name = "passport_number")
    private String passportNumber;
    @OneToOne(mappedBy = "user")
    private Wallet wallet;

}

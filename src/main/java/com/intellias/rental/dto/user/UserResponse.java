package com.intellias.rental.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String firstName;
    private String surname;
    private LocalDate dob;
    private Integer age;
    private String passportNumber;
    private String email;
    private boolean isEmailConfirmed;
    private int walletAmount;
}

package com.intellias.rental.dto.rent;

import com.intellias.rental.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data

public class ConfirmedRentResponse {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RentStatus status;
    private int totalAmount;
}

package com.intellias.rental.dto.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DepositResponse {
    private String message;
}

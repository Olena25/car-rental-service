package com.intellias.rental.dto.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.UUID;

@Data
public class DepositRequest {
    private UUID depositCode;
    @Min(1)
    private int amount;
}

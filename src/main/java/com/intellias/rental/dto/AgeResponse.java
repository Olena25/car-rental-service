package com.intellias.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AgeResponse {

    private String name;
    private Integer age;
    private long count;

}

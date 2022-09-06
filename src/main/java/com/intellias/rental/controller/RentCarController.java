package com.intellias.rental.controller;

import com.intellias.rental.dto.rent.ConfirmedRentRequest;
import com.intellias.rental.dto.rent.ConfirmedRentResponse;
import com.intellias.rental.dto.rent.RentCarRequest;
import com.intellias.rental.dto.rent.RentCarResponse;
import com.intellias.rental.service.RentCarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RentCarController {

    private RentCarService rentCarService;

    @PostMapping("/users/{userId}/cars/{carId}/book")
    public RentCarResponse rentCar(@PathVariable int userId,
                                   @PathVariable int carId,
                                   @RequestBody RentCarRequest rentCarRequest){
        return rentCarService.bookCar(userId,carId,rentCarRequest);
    }
    @PostMapping("/rent/confirm")
    public ConfirmedRentResponse confirmRent(@RequestBody ConfirmedRentRequest confirmedRentRequest){
       return rentCarService.confirmRent(confirmedRentRequest);
    }
}

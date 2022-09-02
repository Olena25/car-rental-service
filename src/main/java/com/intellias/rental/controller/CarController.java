package com.intellias.rental.controller;

import com.intellias.rental.dto.CarResponse;
import com.intellias.rental.enums.Group;
import com.intellias.rental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarController {
    private CarService carService;

    @GetMapping("/cars")
    public List<CarResponse> finaAllAvailableCarsForRent() {
        return carService.findAvailableCarsForRent();
    }

    @GetMapping("/cars-group")
    public List<CarResponse> findCarsByGroup(@RequestParam Group group) {
        return carService.findCarsInGroup(group);
    }

    @GetMapping("/cars-prices")
    public List<CarResponse> findCarsByPricePerDay(@RequestParam int min, @RequestParam int max){
       return carService.findCarsByPricePerDay(min, max);
    }
}

package com.intellias.rental.service;

import com.intellias.rental.db.CarRepository;
import com.intellias.rental.dto.CarResponse;
import com.intellias.rental.enums.CarStatus;
import com.intellias.rental.enums.Group;
import com.intellias.rental.mapper.CarMapper;
import com.intellias.rental.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class CarService {
 private CarMapper carMapper;
 private CarRepository carRepository;

 public List<CarResponse> findAvailableCarsForRent(){
     List<Car> cars = carRepository.findAllByStatus(CarStatus.AVAILABLE);
     return carMapper.mapToCarResponses(cars);
 }

 public List<CarResponse> findCarsInGroup(Group group) {
     List<Car> cars = carRepository.findAllByGroup(group);
     return carMapper.mapToCarResponses(cars);
 }

 public List<CarResponse> findCarsByPricePerDay(int min, int max) {
     List<Car> cars = carRepository.findAllByPricePerDay(min, max);
     return carMapper.mapToCarResponses(cars);
 }
}

package com.intellias.rental.service;

import com.intellias.rental.db.CarRepository;
import com.intellias.rental.db.RentedCarRepository;
import com.intellias.rental.db.UserRepository;
import com.intellias.rental.dto.rent.RentCarRequest;
import com.intellias.rental.dto.rent.RentCarResponse;
import com.intellias.rental.enums.CarStatus;
import com.intellias.rental.enums.RentStatus;
import com.intellias.rental.exception.CarAlreadyRentedException;
import com.intellias.rental.exception.CarNotFoundException;
import com.intellias.rental.exception.UserHasActiveRentException;
import com.intellias.rental.exception.UserNotFoundException;
import com.intellias.rental.mapper.RentCarMapper;
import com.intellias.rental.model.Car;
import com.intellias.rental.model.RentedCar;
import com.intellias.rental.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentCarService {

    private CarRepository carRepository;
    private RentedCarRepository rentedCarRepository;
    private UserRepository userRepository;
    private RentCarMapper rentCarMapper;

    public RentCarResponse rentCar(int userId, int carId, RentCarRequest rentCarRequest){
        
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);

        rentedCarRepository.findAllByUser(user)
                .stream()
                .filter(rentedCar -> !rentedCar.getRentStatus().equals(RentStatus.RETURNED))
                .findFirst()
                .ifPresent(rentedCar -> {
                    throw new UserHasActiveRentException();
                });

        if(car.getStatus().equals(CarStatus.RENTED)) {
            throw new CarAlreadyRentedException();
        }

        RentedCar rentedCar = rentCarMapper.mapToRentedCar(user, car, rentCarRequest, RentStatus.BOOKED);
        rentedCar = rentedCarRepository.save(rentedCar);

        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        RentCarResponse rentCarResponse = new RentCarResponse();
        rentCarResponse.setRentId(rentedCar.getId());
        return rentCarResponse;
    }
}

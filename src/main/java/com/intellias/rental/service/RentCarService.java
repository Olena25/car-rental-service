package com.intellias.rental.service;

import com.intellias.rental.db.CarRepository;
import com.intellias.rental.db.RentedCarRepository;
import com.intellias.rental.db.UserRepository;
import com.intellias.rental.db.WalletRepository;
import com.intellias.rental.dto.rent.ConfirmedRentRequest;
import com.intellias.rental.dto.rent.ConfirmedRentResponse;
import com.intellias.rental.dto.rent.RentCarRequest;
import com.intellias.rental.dto.rent.RentCarResponse;
import com.intellias.rental.enums.CarStatus;
import com.intellias.rental.enums.RentStatus;
import com.intellias.rental.enums.TransactionType;
import com.intellias.rental.exception.*;
import com.intellias.rental.mapper.RentCarMapper;
import com.intellias.rental.model.Car;
import com.intellias.rental.model.RentedCar;
import com.intellias.rental.model.User;
import com.intellias.rental.model.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentCarService {

    @Value("${car-rental.limit-rent-days}")
    private Integer daysLimit;
    @Value("${car-rental.age-for-tax}")
    private int ageForTax;
    @Value("${car-rental.tax}")
    private double tax;

    private final CarRepository carRepository;
    private final RentedCarRepository rentedCarRepository;
    private final UserRepository userRepository;
    private final RentCarMapper rentCarMapper;
    private final WalletRepository walletRepository;
    private final DepositService depositService;

    public RentCarResponse bookCar(int userId, int carId, RentCarRequest rentCarRequest) {
        User user = userRepository.findUserByIdAndIsEmailConfirmed(userId, true)
                .orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);

        long rentingDays = DAYS.between(rentCarRequest.getStartDate(), rentCarRequest.getEndDate());

        if (rentingDays <= 0) {
            log.info("Start date cannot be after end date for renting userId {}", userId);

            throw new RentDatesException("Start date and end date is not correct");
        } else if (rentingDays > daysLimit) {
            throw new RentDurationLimitExceedException("Limit for rent is " + daysLimit + " days");
        }

        long rentCarPrice = car.getPricePerDay() * rentingDays;

        if (user.getWallet().getAmount() < rentCarPrice) {
            throw new NotEnoughMoneyForRentException(rentCarPrice);
        }

        rentedCarRepository.findAllByUser(user)
                .stream()
                .filter(rentedCar -> !rentedCar.getRentStatus().equals(RentStatus.RETURNED))
                .findFirst()
                .ifPresent(rentedCar -> {
                    throw new UserHasActiveRentException();
                });

        if (car.getStatus().equals(CarStatus.RENTED)) {
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

    public ConfirmedRentResponse confirmRent(ConfirmedRentRequest confirmedRentRequest) {
        RentedCar bookedCar = rentedCarRepository.findByPassportNumber(confirmedRentRequest.getPassportNumber())
                .stream()
                .filter(rentedCar -> rentedCar.getRentStatus().equals(RentStatus.BOOKED))
                .findFirst()
                .orElseThrow(() -> new RentIsNotFoundForUserException(confirmedRentRequest.getPassportNumber()));


        long rentingDays = DAYS.between(bookedCar.getStartDate(), bookedCar.getEndDate());
        int rentCarPrice = bookedCar.getCar().getPricePerDay() * (int) rentingDays;
        if (bookedCar.getUser().getAge() < ageForTax) {
            rentCarPrice = (int) (rentCarPrice + (rentCarPrice * tax));
        }


        Wallet wallet = bookedCar.getUser().getWallet();

        int newAmountWallet = wallet.getAmount() - rentCarPrice;

        wallet.setAmount(newAmountWallet);
        walletRepository.save(wallet);

        depositService.createNewTransaction(rentCarPrice, wallet, TransactionType.CAR_PAYMENT);

        bookedCar.setRentStatus(RentStatus.CONFIRMED);
        rentedCarRepository.save(bookedCar);

        ConfirmedRentResponse confirmedRentResponse =
                new ConfirmedRentResponse(bookedCar.getStartDate(), bookedCar.getEndDate(), bookedCar.getRentStatus(), rentCarPrice);
        return confirmedRentResponse;
    }
}

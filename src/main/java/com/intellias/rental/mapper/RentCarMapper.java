package com.intellias.rental.mapper;

import com.intellias.rental.dto.rent.RentCarRequest;
import com.intellias.rental.enums.RentStatus;
import com.intellias.rental.model.Car;
import com.intellias.rental.model.RentedCar;
import com.intellias.rental.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentCarMapper {

    @Mapping(target = "id", ignore = true)
    RentedCar mapToRentedCar(User user, Car car, RentCarRequest rentCarRequest, RentStatus rentStatus);
}

package com.intellias.rental.mapper;

import com.intellias.rental.dto.CarResponse;
import com.intellias.rental.model.Car;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    List<CarResponse> mapToCarResponses(List<Car> cars);
    CarResponse mapToCarResponse(Car car);

}

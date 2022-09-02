package com.intellias.rental.db;

import com.intellias.rental.enums.CarStatus;
import com.intellias.rental.enums.Group;
import com.intellias.rental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
    @Query("SELECT c FROM car c WHERE c.status = :status")
    List<Car> findAllByStatus(@Param("status") CarStatus status);

    @Query("SELECT c FROM car c WHERE c.group = :group")
    List<Car> findAllByGroup(@Param("group") Group group);

    @Query("SELECT c FROM car c WHERE c.pricePerDay BETWEEN :min and :max")
    List<Car> findAllByPricePerDay(@Param("min") int min, @Param("max") int max);



}

package com.intellias.rental.db;

import com.intellias.rental.model.RentedCar;
import com.intellias.rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentedCarRepository extends JpaRepository<RentedCar,Integer> {
    List<RentedCar> findAllByUser(User user);

    @Query("SELECT r FROM rented_car r WHERE r.user.passportNumber = :passportNumber")
    List<RentedCar> findByPassportNumber(@Param("passportNumber") String passportNumber);

}

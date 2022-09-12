package com.intellias.rental.db;

import com.intellias.rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT DISTINCT u FROM user u WHERE u.passportNumber = :passport")
    Optional<User> findUserByPassportNumber(@Param("passport") String passportNumber);

    long countByEmail(String email);

    Optional<User> findUserByIdAndIsEmailConfirmed(int id, boolean emailConfirmed);
}

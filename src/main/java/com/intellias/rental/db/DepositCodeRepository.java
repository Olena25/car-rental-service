package com.intellias.rental.db;

import com.intellias.rental.model.DepositCode;
import com.intellias.rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepositCodeRepository extends JpaRepository<DepositCode,Integer> {
  Optional<DepositCode> findByUserAndCode(User user, UUID code);
}

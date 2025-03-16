package com.example.TradingAlert.Repository;

import com.example.TradingAlert.Dto.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findUserById(String id);
}
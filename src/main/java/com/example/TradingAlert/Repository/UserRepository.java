package com.example.TradingAlert.Repository;

import com.example.TradingAlert.Dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserById(String id);
}
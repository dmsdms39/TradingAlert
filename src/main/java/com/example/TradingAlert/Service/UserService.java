package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.User;
import com.example.TradingAlert.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void addUser(User user) {

        if (userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("Username already exists: " + user.getId());
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
        }
    }
}
package com.example.TradingAlert.Controller;

import com.example.TradingAlert.Dto.User;
import com.example.TradingAlert.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registrationUser(@RequestBody @Valid User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.status(201).body("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Add User failed: " + e.getMessage());
        }
    }
}

package com.example.TradingAlert.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String email;
    private String token;
}
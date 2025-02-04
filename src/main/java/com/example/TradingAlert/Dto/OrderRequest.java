package com.example.TradingAlert.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotBlank
    private String stockCode;
    @Min(1)
    private int quantity;
    @Min(1)
    private int price;
    @Pattern(regexp = "^(?i)(buy|sell)$")
    private String  action;

    public OrderRequest(String stockCode, int quantity, int price, String action) {
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.price = price;
        this.action = action;
    }
}

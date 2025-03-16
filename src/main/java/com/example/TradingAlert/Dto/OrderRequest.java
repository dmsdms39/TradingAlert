package com.example.TradingAlert.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank
    private String stockCode;
    @NotBlank
    private String userId;
    @Min(1)
    private long quantity;
    @Min(1)
    private int price;
    @Pattern(regexp = "(BUY|SELL)$")
    private String  action;

    public OrderRequest(String stockCode, String userId, long quantity, int price, String action) {
        this.stockCode = stockCode;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.action = action;
    }
}

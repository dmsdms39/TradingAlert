package com.example.TradingAlert.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long orderId;
    private String message;
    private String stockCode;
    private long quantity;
}

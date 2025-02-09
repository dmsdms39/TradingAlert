package com.example.TradingAlert.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "TradeResult")
public class TradeResult {
    @Id
    private Long orderId;
    private String stockCode;
    private long quantity;
    private int executedPrice;
    private String status; // MATCHED or PENDING

    @Override
    public String toString() {
        return "TradeResult{" +
                "orderId='" + orderId + '\'' +
                "stockCode='" + stockCode + '\'' +
                ", quantity=" + quantity +
                ", executedPrice=" + executedPrice +
                ", status='" + status + '\'' +
                '}';
    }
}

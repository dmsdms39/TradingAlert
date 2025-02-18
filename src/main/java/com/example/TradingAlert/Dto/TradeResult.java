package com.example.TradingAlert.Dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "tradeResult")
public class TradeResult {

    @Id
    private String executedId;
    private String stockCode;
    private long quantity;
    private int executedPrice;
    private String status;

    public TradeResult (Long buyOrderId, Long sellOrderId, String stockCode, int executedPrice) {
        this.executedId = buyOrderId+"_"+sellOrderId;
        this.stockCode = stockCode;
        this.quantity = 0;
        this.executedPrice = executedPrice;
        this.status = "MATCHED";
    }

}

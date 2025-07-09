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
    private String buyUserId;
    private String sellUserId;
    private String stockCode;
    private long quantity;
    private int executedPrice;
    private String status;

    public TradeResult(TradeStock buyOrder, TradeStock sellOrder) {
        this.executedId = buyOrder.getOrderId()+"_"+sellOrder.getOrderId();
        this.buyUserId = buyOrder.getUserId();
        this.sellUserId = sellOrder.getUserId();
        this.stockCode = buyOrder.getStockCode();
        this.status = "MATCHED";
    }
}

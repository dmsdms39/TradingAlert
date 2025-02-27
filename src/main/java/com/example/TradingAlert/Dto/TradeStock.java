package com.example.TradingAlert.Dto;

import com.example.TradingAlert.Util.SnowflakeIdGenerator;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "orderBook:details")
public class TradeStock {

    @Id
    private Long orderId;
    private String stockCode;
    private long quantity;
    private int price;
    private String  action;


    //OrderRequest를 사용하는 생성자
    public TradeStock(OrderRequest request) {
        this.orderId = SnowflakeIdGenerator.getInstance().generateId();
        this.stockCode = request.getStockCode();
        this.quantity = request.getQuantity();
        this.price = request.getAction().equals("BUY") ? -1 * Math.abs(request.getPrice()) : Math.abs(request.getPrice());
        this.action = request.getAction();
    }

}

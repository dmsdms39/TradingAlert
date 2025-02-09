package com.example.TradingAlert.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeStock {
//    private static final AtomicLong counter = new AtomicLong(0); // 1부터 시작

    private Long orderId;
    private String stockCode;
    private long quantity;
    private int price;
    private String  action;

    //OrderRequest를 사용하는 생성자
    public TradeStock(OrderRequest request) {
//        this.orderId = counter.getAndIncrement();
        this.orderId = Instant.now().toEpochMilli() + System.nanoTime();
        this.stockCode = request.getStockCode();
        this.quantity = request.getQuantity();
        this.price = request.getPrice();
        this.action = request.getAction();
    }

//    public TradeStock(
//            @JsonProperty("stockCode") Long orderId,
//            @JsonProperty("stockCode") String stockCode,
//            @JsonProperty("quantity") int quantity,
//            @JsonProperty("price") int price,
//            @JsonProperty("action") String action){
//        this.orderId = orderId;
//        this.stockCode = stockCode;
//        this.quantity = quantity;
//        this.price = price;
//        this.action = action;
//    }

//    @Override
//    public String toString() {
//        return "TradeStock{" +
//                "orderId='" + orderId + '\'' +
//                "stockCode='" + stockCode + '\'' +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                ", action='" + action + '\'' +
//                '}';
//    }

}

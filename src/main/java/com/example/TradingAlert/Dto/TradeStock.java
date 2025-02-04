package com.example.TradingAlert.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TradeStock {
    private String stockCode;
    private int quantity;
    private int price;
    private String  action;

    public TradeStock(@JsonProperty("stockCode") String stockCode,
                      @JsonProperty("quantity") int quantity,
                      @JsonProperty("price") int price,
                      @JsonProperty("action") String action){
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.price = price;
        this.action = action;
    }

    @Override
    public String toString() {
        return "StockOrder{" +
                "stockCode='" + stockCode + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", action='" + action + '\'' +
                '}';
    }
}

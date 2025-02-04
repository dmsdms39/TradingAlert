package com.example.TradingAlert.Repository;

import com.example.TradingAlert.Dto.TradeStock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.time.Duration;

@Repository
public class OrderExecutionRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final int EXPIRATION_TIME = 60; // 데이터 TTL (초 단위)

    public OrderExecutionRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    // 체결 데이터 저장
    public void saveOrderExecution(TradeStock orderExecution) {
        try {
            String key = "order:execution:" + orderExecution.getStockCode();
            String value = objectMapper.writeValueAsString(orderExecution);
            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(EXPIRATION_TIME));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 저장 오류", e);
        }
    }

    // 체결 데이터 조회
    public TradeStock findOrderExecution(String orderId) {
        try {
            String key = "order:execution:" + orderId;
            String value = (String) redisTemplate.opsForValue().get(key);
            return value != null ? objectMapper.readValue(value, TradeStock.class) : null;
        } catch (Exception e) {
            throw new RuntimeException("Redis 조회 오류", e);
        }
    }

    // 체결 데이터 삭제
    public void deleteOrderExecution(String orderId) {
        String key = "order:execution:" + orderId;
        redisTemplate.delete(key);
    }
}

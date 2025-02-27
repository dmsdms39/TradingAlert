package com.example.TradingAlert.Repository;

import com.example.TradingAlert.Dto.TradeStock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class OrderBookRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final OrderDetailsRepository orderDetailsRepository;;

    // ZSET Key 생성 (네임스페이스 적용)
    private String getKey(String stockCode, String action) {
        return String.format("orderBook:%s:%s", stockCode, action);
    }

    // 1. ZSET에 주문 추가
    public void addTradeStock(TradeStock order) {
        String key = getKey(order.getStockCode(), order.getAction());
        long orderId = order.getOrderId();

        // price를 score로, orderId를 value로 저장
        redisTemplate.opsForZSet().add(key, String.valueOf(orderId), order.getPrice());
        orderDetailsRepository.save(order);
    }

    // 2. 최저가 주문 조회
    public TradeStock getLowestPriceOrder(String stockCode, String action) {
        String key = getKey(stockCode, action);

        // score(가격)를 기준으로 최소값을 먼저 조회
        Set<ZSetOperations.TypedTuple<String>> lowestOrder = redisTemplate.opsForZSet().rangeWithScores(key, 0, 0);
        if (lowestOrder == null || lowestOrder.isEmpty()) return null;

        Iterator<ZSetOperations.TypedTuple<String>> iterator = lowestOrder.iterator();
        if (!iterator.hasNext()) {
            return null;  // 이터레이터가 비어 있으면 null 반환
        }

        String orderIdStr = iterator.next().getValue();
        long orderId;

        try {
            orderId = Long.parseLong(orderIdStr);
        } catch (NumberFormatException e) {
            return null;  // 변환 실패 시 null 반환
        }

        Optional<TradeStock> optionalStock = orderDetailsRepository.findById(orderId);
        return optionalStock.orElse(null);
    }

    // 3. 최저가 삭제
    public void removeLowestPriceOrder(String stockCode, String action) {
        String key = getKey(stockCode, action);
        Set<ZSetOperations.TypedTuple<String>> lowestOrder = redisTemplate.opsForZSet().popMin(key, 1);
        if (lowestOrder == null || lowestOrder.isEmpty()) return;
//        삭제 안함
//        orderDetailsRepository.deleteById(Long.valueOf(Objects.requireNonNull(lowestOrder.iterator().next().getValue())));
    }
}

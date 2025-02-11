package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Dto.TradeStock;
import com.example.TradingAlert.Repository.TradeResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final ObjectMapper objectMapper ;
    private final MatchingService matchingService;
    private final AlertService alertService;
    private final TradeResultRepository tradeResultRepository;;

    public void processOrder(TradeStock order) {
        // 주문 체결 처리
        TradeResult tradeResult = matchingService.matchOrder(order);
        System.out.println(" [processOrder] Matched order: " + tradeResult);

        // 체결 결과 레디스 저장 & 알람 전송
        tradeResultRepository.save(tradeResult);
        System.out.println(" [processOrder] Saved order: " + tradeResult);
        alertService.sendTradeAlert(tradeResult);
    }
}
//@rabbitmq 어노테이션 달아야할듯
//public class OrderProcessorService {
//
//    private final static String QUEUE_NAME = "order_queue";
//
//    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Channel channel;
//        try (Connection connection = factory.newConnection()) {
//            channel = connection.createChannel();
//
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            System.out.println(" [*] Waiting for orders. To exit press CTRL+C");
//
//                // 현재 가격 조회
//                int currentPrice = 0;
//                try {
//                    currentPrice = OrderExecutionService.getCurrentPrice(order.getStockCode());
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(" [x] Current price of " + order.getStockCode() + ": " + currentPrice);
//
//                // 주문 조건 확인
//                if ((order.getAction().equals("BUY") && currentPrice <= order.getPrice()) ||
//                        (order.getAction().equals("SELL") && currentPrice >= order.getPrice())) {
//                    // 주문 체결
//                    System.out.println(" [x] Order executed: " + order);
//                    // 체결 결과를 반환하거나 다른 큐에 전송
//                } else {
//                    System.out.println(" [x] Order not executed. Price condition not met.");
//                }
//            };
//
//            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
//            });
//        }
//    }
//}
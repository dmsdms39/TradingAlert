package com.example.TradingAlert.Service;
import com.example.TradingAlert.Dto.TradeStock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TradeProcessorService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "order_queue")
    public void processOrder(String message) {
        try {
            // JSON을 TradeStock 객체로 변환
            TradeStock order = objectMapper.readValue(message, TradeStock.class);
            System.out.println(" [x] Received order: " + order);

            // 현재 가격 조회
            int currentPrice = TradeExecutionService.getCurrentPrice(order.getStockCode());
            System.out.println(" [x] Current price of " + order.getStockCode() + ": " + currentPrice);

            // 주문 체결 여부 확인
            if ((order.getAction().equals("BUY") && currentPrice <= order.getPrice()) ||
                    (order.getAction().equals("SELL") && currentPrice >= order.getPrice())) {
                System.out.println(" [x] Order executed: " + order);
            } else {
                System.out.println(" [x] Order not executed. Price condition not met.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
//            //주문 처리 콜백 정의
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                // JSON 메시지를 StockOrder 객체로 변환
//                String json = new String(delivery.getBody(), "UTF-8");
//                ObjectMapper objectMapper = new ObjectMapper();
//                OrderStock order = objectMapper.readValue(json, OrderStock.class);
//
//                System.out.println(" [x] Received order: " + order);
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
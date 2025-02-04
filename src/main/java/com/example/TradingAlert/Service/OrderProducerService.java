package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    public OrderProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(OrderRequest order) {
        try {
            String jsonOrder = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(queueName, jsonOrder);
            System.out.println(" [x] Sent Order: " + jsonOrder);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

//@Service
//public class OrderProducerService {
//
//    private final static String QUEUE_NAME = "order_queue";
//
//    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try (Connection connection = factory.newConnection();
//             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            // 주문 생성
//            OrderStock order = new OrderStock("005930", 10, 50000, "BUY");
//
//            // 객체를 JSON으로 직렬화
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(order);
//
//            // 큐에 전송
//            channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
//            System.out.println(" [x] Sent order: " + json);
//        }
//    }
//}
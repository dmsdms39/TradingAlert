package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeStock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    public void sendOrder(TradeStock order) {
        try {
            System.out.println(" [OrderService] Sent Order: " + order);
            String jsonOrder = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(queueName, jsonOrder);
            System.out.println(" [OrderService] Sent Order: " + jsonOrder);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

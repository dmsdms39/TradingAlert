package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeStock;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.order}")
    private String queueName;

    public void sendOrder(TradeStock order) {
            rabbitTemplate.convertAndSend(queueName, order);
            System.out.println(" [OrderService] Sent Order: " + order);
    }
}

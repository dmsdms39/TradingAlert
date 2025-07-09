package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.alert}")
    private String queueName;

    public void sendTradeAlert(TradeResult tradeResult) {
        rabbitTemplate.convertAndSend(queueName, tradeResult);
        System.out.println("[AlertService] Alert sent: " + tradeResult);
    }
}
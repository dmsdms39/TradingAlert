package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeStock;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RabbitMQListener {

    private final TradingService  tradingService;

    @RabbitListener(queues = "order_queue")
    public void receiveMessage(TradeStock message) {
        System.out.println(" [RabbitMQListener] Received message: " + message);
        tradingService.processOrder(message);
    }
}

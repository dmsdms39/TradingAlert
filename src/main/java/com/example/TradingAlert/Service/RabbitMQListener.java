package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Dto.TradeStock;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RabbitMQListener {

    private final MatchingService  matchingService;
    private final NotificationService notificationService;

    @RabbitListener(queues = "order_queue")
    public void receiveTradeMessage(TradeStock message) {
        System.out.println(" [RabbitMQListener] Received TradeStock message: " + message);
        matchingService.matchOrder(message);
    }

    @RabbitListener(queues = "alert_queue")
    public void receiveTradeResult(TradeResult message) {
        System.out.println(" [RabbitMQListener] Received TradeResult message: " + message);
        notificationService.sendNotification( "TradeResult Alarm Notification", message);
    }
}

package com.example.TradingAlert;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Dto.TradeStock;
import com.example.TradingAlert.Service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "spring.rabbitmq.queue.alert=alert_queue")
public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        // queueName 필드  주입
        ReflectionTestUtils.setField(alertService, "queueName", "alert_queue");
    }

    @Test
    void sendTradeAlert_ShouldSendMessageToQueue() {
        TradeStock buyStock = new TradeStock(1L, "eun", "005930", 100, -15000, "BUY");
        TradeStock sellStock = new TradeStock(2L, "hee", "005930", 100, 15000, "SELL");
        TradeResult tradeResult = new TradeResult(buyStock, sellStock);

        alertService.sendTradeAlert(tradeResult);

        // RabbitMQ로 메시지 전송 확인
        verify(rabbitTemplate, times(1)).convertAndSend(eq("alert_queue"), eq(tradeResult));
    }
}

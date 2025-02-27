package com.example.TradingAlert;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Service.AlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendTradeAlert_ShouldSendMessageToQueue() {
        TradeResult tradeResult = new TradeResult((long)11440031, (long)15152200, "005930", 15000);
        alertService.sendTradeAlert(tradeResult);

        // RabbitMQ로 메시지가 전송 확인
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), eq(tradeResult));
    }
}

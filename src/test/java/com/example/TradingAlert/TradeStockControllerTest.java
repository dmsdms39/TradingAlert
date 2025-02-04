package com.example.TradingAlert;

import com.example.TradingAlert.Service.TradeExecutionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(TradeStockController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeExecutionService tradeExecutionService; // Mock 객체 주입

    @Test
    public void testGetAvailableStock() throws Exception {
        // Mocking: stockCode = "005930"일 때, 100을 반환하도록 설정
        Mockito.when(tradeExecutionService.getCurrentPrice("005930")).thenReturn(100);

        // API 호출 및 검증
        mockMvc.perform(get("/inventory")
                        .param("stockCode", "005930"))
                .andExpect(status().isOk())
                .andExpect(content().string("Available stock for 005930: 100"));
    }
}

package com.example.TradingAlert;

import com.example.TradingAlert.Controller.StockController;
import com.example.TradingAlert.Dto.OrderRequest;
import com.example.TradingAlert.Service.MatchingService;
import com.example.TradingAlert.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StockController.class)
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAvailableStock() throws Exception {
        try (MockedStatic<MatchingService> mockedStatic = mockStatic(MatchingService.class)) {
            mockedStatic.when(() -> MatchingService.getCurrentPrice(any())).thenReturn(100);

            // API 호출 및 검증
            mockMvc.perform(get("/trading/inventory")
                            .param("stockCode", "005930"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Available stock for 005930: 100"));

        }
    }

    @Test
    public void sendOrder_ShouldReturnSuccessResponse() throws Exception {
        OrderRequest request = new OrderRequest( "005930", 100, 15000, "BUY");

        mockMvc.perform(post("/trading/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", greaterThan(10000000000L)))
                .andExpect(jsonPath("$.message").value("Order successfully placed"))
                .andExpect(jsonPath("$.stockCode").value("005930"))
                .andExpect(jsonPath("$.quantity").value(100));
    }

}

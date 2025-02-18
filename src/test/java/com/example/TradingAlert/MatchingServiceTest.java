package com.example.TradingAlert;

import com.example.TradingAlert.Dto.TradeStock;
import com.example.TradingAlert.Repository.OrderBookRepository;
import com.example.TradingAlert.Repository.OrderDetailsRepository;
import com.example.TradingAlert.Repository.TradeResultRepository;
import com.example.TradingAlert.Service.AlertService;
import com.example.TradingAlert.Service.MatchingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchingServiceTest {

    @InjectMocks
    private MatchingService matchingService;

    @Mock
    private OrderBookRepository orderBookRepository;

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @Mock
    private TradeResultRepository tradeResultRepository;

    @Mock
    private AlertService alertService;

    private TradeStock buyOrder;
    private TradeStock sellOrder;

    @BeforeEach
    void setUp() {
        buyOrder = new TradeStock(1L, "005930", 100, -15000, "BUY"); // 매수 주문 (음수 가격)
        sellOrder = new TradeStock(2L, "005930", 100, 15000, "SELL"); // 매도 주문 (양수 가격)
    }

    @Test
    void matchOrder_ShouldMatchBuyAndSellOrders() {
        // 가짜 데이터 가정
        when(orderBookRepository.getLowestPriceOrder("005930", "SELL")).thenReturn(sellOrder);

        // 주문을 매칭시킴
        matchingService.matchOrder(buyOrder);

        // 주문이 체결되었는지 검증
        verify(tradeResultRepository, times(1)).save(any());
        verify(alertService, times(1)).sendTradeAlert(any());

        // 매칭 후 매도 주문이 삭제되었는지 확인
        verify(orderBookRepository, times(1)).removeLowestPriceOrder("005930", "SELL");
    }
}

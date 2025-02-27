package com.example.TradingAlert.Controller;

import com.example.TradingAlert.Dto.OrderRequest;
import com.example.TradingAlert.Dto.OrderResponse;
import com.example.TradingAlert.Dto.TradeStock;
import com.example.TradingAlert.Service.MatchingService;
import com.example.TradingAlert.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trading")
@RequiredArgsConstructor
public class StockController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> sendOrder(@RequestBody @Valid OrderRequest request) {
        try {
            TradeStock  tradeStock = new TradeStock(request);
            orderService.sendOrder(tradeStock);

            OrderResponse response = OrderResponse.builder()
                    .orderId(tradeStock.getOrderId())
                    .message("Order successfully placed")
                    .stockCode(request.getStockCode())
                    .quantity(request.getQuantity())
                    .build();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    OrderResponse.builder()
                    .message("Order failed : " + e.getMessage())
                    .build());
        }
    }
    @GetMapping("/inventory")
    public String getAvailableStock(@RequestParam String stockCode) {
        int available = MatchingService.getCurrentPrice(stockCode);
        return "Available stock for " + stockCode + ": " + available;
    }
}


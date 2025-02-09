package com.example.TradingAlert.Controller;

import com.example.TradingAlert.Dto.OrderRequest;
import com.example.TradingAlert.Dto.TradeStock;
import com.example.TradingAlert.Service.MatchingService;
import com.example.TradingAlert.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/trading")
public class StockController {

    private final OrderService orderService;

    public StockController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody @Valid OrderRequest request) {
        try {
            TradeStock  newOrder = new TradeStock(request);
            orderService.sendOrder(newOrder);

            // JSON 응답 반환
            return ResponseEntity.ok(Map.of(
                    "message", "Order successfully placed",
                    "orderId", newOrder.getOrderId(),
                    "stockCode", request.getStockCode(),
                    "quantity", request.getQuantity()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Order failed",
                    "error", e.getMessage()
            ));
        }
    }
    @GetMapping("/inventory")
    public String getAvailableStock(@RequestParam String stockCode) {
        int available = MatchingService.getCurrentPrice(stockCode);
        return "Available stock for " + stockCode + ": " + available;
    }
}


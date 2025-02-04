package com.example.TradingAlert.Controller;

import com.example.TradingAlert.Dto.OrderRequest;
import com.example.TradingAlert.Service.OrderProducerService;
import com.example.TradingAlert.Service.TradeExecutionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trading")

public class TradeStockController {

//    private final StockService stockService;
//    private final TradeExecutionService tradeExecutionService;
    private final OrderProducerService orderProducerService;

    @Autowired
    public TradeStockController(OrderProducerService orderProducerService) {
        this.orderProducerService = orderProducerService;
    }

//    @PostMapping("/order")
//    public String placeOrder(@RequestBody @Valid OrderRequest request) {
//        try {
////            stockService.placeOrder(request.getStockCode(), request.getQuantity(), request.getPrice(), request.getAction());
//            tradeExecutionService.tradeExecution(request.getStockCode());
//            return "Order successful: " + request.getStockCode() + " x " + request.getQuantity();
//        } catch (Exception e){
//            return "Order failed: " + e;
//        }
//    }
    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid OrderRequest request) {
        try {
            orderProducerService.sendOrder(request);
            return ResponseEntity.ok("Order successfully:" + request.getStockCode() + " x " + request.getQuantity());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Order failed: " + e.getMessage());
        }
    }
    @GetMapping("/inventory")
    public String getAvailableStock(@RequestParam String stockCode) {
        int available = TradeExecutionService.getCurrentPrice(stockCode);
        return "Available stock for " + stockCode + ": " + available;
    }
}


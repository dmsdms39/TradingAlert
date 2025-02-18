package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Dto.TradeStock;
import com.example.TradingAlert.Repository.OrderBookRepository;
import com.example.TradingAlert.Repository.OrderDetailsRepository;
import com.example.TradingAlert.Repository.TradeResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final OrderBookRepository orderBookRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final TradeResultRepository tradeResultRepository;
    private final AlertService alertService;

    @Setter(onMethod_ = {@Autowired})
    public static Map<String, Integer> currentPrice = new HashMap<>();

    public void matchOrder(TradeStock order) {

        while (order.getQuantity() > 0){
            // 매칭되는 값이 있는지 결과 가져옴
            TradeResult tradeResult = matchPriceLogic(order);

            if (tradeResult != null) {
                // 체결 결과 레디스 저장 & 알람 전송
                order.setQuantity(order.getQuantity() - tradeResult.getQuantity());
                tradeResultRepository.save(tradeResult);
                alertService.sendTradeAlert(tradeResult);
                System.out.println(" [matchOrder] Saved result: " + tradeResult);
            }
            else{
                //체결되지 않은 주문이 남아있는 경우
                if (order.getQuantity() > 0){
                    orderBookRepository.addTradeStock(order);
                    System.out.println(" [matchOrder] Matched Order not found: " + order);
                }
                return;
            }
        }
    }

    private TradeResult matchPriceLogic(TradeStock order) {
        String oppositeAction = order.getAction().equals("BUY") ? "SELL" : "BUY";
        TradeStock refStock = orderBookRepository.getLowestPriceOrder(order.getStockCode(), oppositeAction);
        int adjustedPrice = -1 * order.getPrice();

        //상대 주문이 없으면 null
        if (refStock == null)  return null;

        // 주문 가격이 refPrice 보다 크거나 같으면 매칭
        if (refStock.getPrice() <= adjustedPrice) {

            //tradeResult 생성
            TradeResult tradeResult = Optional.of(order)
                    .map( o -> o.getAction().equals("BUY") ?
                            new TradeResult(o.getOrderId(), refStock.getOrderId(),
                                    o.getStockCode(), refStock.getPrice()) :
                            new TradeResult( refStock.getOrderId(), o.getOrderId(),
                                    o.getStockCode(), -1 * refStock.getPrice()
                            )).get();

            setCurrentPrice( new HashMap<>(
                    Map.of(order.getStockCode(), refStock.getPrice())));

            System.out.println(" [match executed: " + order + "]");

            if (order.getQuantity() >= refStock.getQuantity()) {
                //order 원하는 수량 일부 거래가 이루어지는 경우
                tradeResult.setQuantity(refStock.getQuantity());
                order.setQuantity(order.getQuantity() - refStock.getQuantity());

                orderBookRepository.removeLowestPriceOrder(order.getStockCode(), oppositeAction);
                return tradeResult;

            }  else {
                // 전부 거래가 이루어 지는 경우, redStock은 남음
                tradeResult.setQuantity(order.getQuantity());
                refStock.setQuantity(refStock.getQuantity() - order.getQuantity());

                orderDetailsRepository.save(refStock); //refStock quantity 수정
                return tradeResult;
            }
        }
        return null;
    }

    public static int getCurrentPrice(String stockCode) {
        return currentPrice.getOrDefault(stockCode, -1);
    }
}

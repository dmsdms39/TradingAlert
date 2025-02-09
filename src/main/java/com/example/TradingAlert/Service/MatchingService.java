package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Dto.TradeStock;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MatchingService {

    public TradeResult matchOrder(TradeStock order) {
        //체결 후 redis 저장과 알람 작성할 예정
        // 현재 가격 조회
        int currentPrice = getCurrentPrice(order.getStockCode());
        System.out.println(" [matchOrder] Current price of " + order.getStockCode() + ": " + currentPrice);

        // 주문 체결 여부 확인
        boolean isMatched = (order.getAction().equals("BUY") && currentPrice <= order.getPrice()) ||
                (order.getAction().equals("SELL") && currentPrice >= order.getPrice());

        if (isMatched) {
            System.out.println(" [matchOrder Order executed: " + order);
            return new TradeResult(order.getOrderId(), order.getStockCode(), order.getQuantity(), currentPrice, "MATCHED");
        } else {
            System.out.println(" [matchOrder] Order not executed. Price condition not met.");
            return new TradeResult(order.getOrderId(), order.getStockCode(), order.getQuantity(), currentPrice, "PENDING");
        }
    }

    public static int getCurrentPrice(String stockCode) {
        Random random = new Random();
        return random.nextInt(100000) + 10000; // 10,000 ~ 110,000 사이의 랜덤 가격

        // 수정 예정 : mq에서 가격 조회 (API 호출) ** 잘 모르겠음.
//        String apiUrl = "https://api./price";
//        URL url = new URL(apiUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // JSON 응답에서 가격 추출 (예: {"price": 50000})
//            String jsonResponse = response.toString();
//            int price = Integer.parseInt(jsonResponse.split("\"price\":")[1].replace("}", "").trim());
//            return price;
//        } else {
//            throw new RuntimeException("Failed to fetch current price. Response code: " + responseCode);
//        }
    }

}

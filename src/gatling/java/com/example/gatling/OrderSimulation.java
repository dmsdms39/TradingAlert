package com.example.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class OrderSimulation extends Simulation {

    // 기본 HTTP 설정
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // 서버 주소
            .acceptHeader("application/json")
            .disableCaching()
            .shareConnections(); // 요청 헤더 설정

    // 시나리오 정의
//    private ScenarioBuilder scn = scenario("Order Load Test")
//            .exec(http("Place Order")
//                    .post("/trading/order")
//                    .body(StringBody("{\"stockCode\": \"005930\", \"userId\" : \"hee\", \"quantity\" : 1, \"price\" : 50000, \"action\" : \"BUY\"}"))
//                    .asJson()
//                    .check(status().is(200))
//            );

    // Feeder로 사용할 무한 Iterator
    Iterator<Map<String, Object>> randomOrderFeeder = new Iterator<>() {
        @Override
        public boolean hasNext() {
            return true; // 무한 반복
        }

        @Override
        public Map<String, Object> next() {
            return Map.of(
                    "action", randomAction(),
                    "stockCode", randomStockCode(),
                    "userId", randomUserId(),
                    "price", randomPrice(),
                    "quantity", randomQuantity()
            );
        }
    };
    // 2. 시나리오 정의
    ScenarioBuilder scn = scenario("Random Order Load Test")
            .feed(randomOrderFeeder)
            .exec(
                    http("Place Order")
                            .post("/trading/order")
                            .body(StringBody(
                                    """
                                    {
                                      "stockCode": "${stockCode}",
                                      "userId": "${userId}",
                                      "quantity": ${quantity},
                                      "price": ${price},
                                      "action": "${action}"
                                    }
                                    """
                            ))
                            .asJson()
                            .check(status().is(200))
            );

    //서버 띄우고 warm up 테스트 전..  warm up 하고난 뒤.. (warm up)
    {
        setUp(
                scn.injectOpen(
                        rampUsersPerSec(10).to(1000).during(60), // 10→100명/초, 1분간 증가
                        constantUsersPerSec(100).during(120)    // 100명/초 유지 2분
                )
        ).protocols(httpProtocol);
    }

    private static String randomAction() {
        return Math.random() < 0.5 ? "BUY" : "SELL";
    }

    private static String randomStockCode() {
        String[] codes = {"005930", "000660", "035720", "068270"};
        return codes[ThreadLocalRandom.current().nextInt(codes.length)];
    }

    private static String randomUserId() {
        String[] users = {"hee", "eun"};
        return users[ThreadLocalRandom.current().nextInt(users.length)];
    }

    private static int randomPrice() {
        return ThreadLocalRandom.current().nextInt(10000, 90000);
    }

    private static int randomQuantity() {
        return ThreadLocalRandom.current().nextInt(1, 10000);
    }
}

package com.example.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class OrderSimulation extends Simulation {

    // 기본 HTTP 설정
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // 서버 주소
            .acceptHeader("application/json"); // 요청 헤더 설정

    // 시나리오 정의
    private ScenarioBuilder scn = scenario("Order Load Test")
            .exec(http("Place Order")
                    .post("/trading/order")
                    .body(StringBody("{\"stockCode\": \"005930\", \"userId\" : \"hee\", \"quantity\" : 1, \"price\" : 50000, \"action\" : \"BUY\"}"))
                    .asJson()
                    .check(status().is(200))
            );

    // 부하 테스트 설정
    {
        setUp(
                scn.injectOpen(
                        rampUsers(10000).during(30) // 30초 동안 100명 증가
                )
        ).protocols(httpProtocol);
    }
}

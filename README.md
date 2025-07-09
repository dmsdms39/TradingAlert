
## 📈 TradingAlert

### 📝 개요

> 사용자가 증권 매매를 요청하면, 주문이 매칭되어 체결되어 알림을 전송하는 시스템입니다.
>
>
> 대량의 주문 및 알림 처리와 실시간성을 보장하기 위해 메시지 큐와 비동기 처리 등을 활용한 구조로 설계되었습니다.
>

### 🛠️ 기술 스택

| 구분 | 기술 |
| --- | --- |
| 언어 | Java (Spring Boot) |
| 메시징 | RabbitMQ |
| DB | Redis, MySQL |
| 모니터링 | Prometheus + Grafana |
| 성능 테스트 | Gatling |
| 알림 | Firebase Cloud Messaging (FCM) |
| 빌드/배포 | Docker, docker-compose |
| 클라우드/인프라 플랫폼 | Naver Cloud Platform (NCP) |

### 📌 주요 기능

- 사용자 주문 처리 (Buy/Sell)
- 주문 매칭 로직 구현
- 체결 정보 저장 (Redis)
- 비동기 알림 전송 (RabbitMQ + FCM)
- Prometheus로 실시간 모니터링
- Gatling을 통한 성능 테스트 (Peak Load 시나리오 포함)

### 💡 기술적 특징

- 메시지 처리 및 알림 시스템 간의 데이터 흐름은 **RabbitMQ 기반 비동기 구조**로 설계
- 체결 결과 및 실시간 데이터는 **Redis에 영속 저장**
- 주문 ID는 **Snowflake 알고리즘**으로 생성하여 **충돌 없이 고유 ID를 생성**

### 🧱 시스템 아키텍처

<img width="1546" height="955" alt="Image" src="https://github.com/user-attachments/assets/e53d2466-b629-443e-b73a-9ddcdff178c6" />

### 📜**시스템 설계도 역할**

**✔️ Broker Server(증권사 서버)**

1. 주식 주문 서비스
2. 주문 데이터를 처리 및 전달(주식 주문 controller)

**✔️ Message Queue (RabbitMQ)**

1. 주문 데이터를 적재
2. Stock Exchange에 주문 데이터 전달

**✔️ Stock Exchange(주식 거래소)**

1. Matching Engine에 매칭 요청
2. 호가창(Order Book) 관리
3. 체결 결과를 redis에 저장

**✔️ Matching Engine(체결 엔진)**

1. 매수/매도 주문 매칭 서비스
2. 거래 체결 시 처리 결과 반환

**✔️ Mysql/Redis (저장소)**

1. Mysql : 사용자 정보 저장
2. Redis : 알림 메시지 큐, 주문 데이터 큐(호가창)

**✔️ Notification Server(알림 서버)**

1. 사용자에게 체결 결과 FCM 알람 출력

### 🔁 주문 처리 흐름도

<img width="1468" height="734" alt="Image" src="https://github.com/user-attachments/assets/66d94c54-4a92-41e2-90c7-af51f30f3ee7" />

```
[User] → 주문 요청
↓
[Broker] → 주문을 RabbitMQ에 저장
↓
[Stock Exchange] → 큐에서 주문 수신, 매칭 처리
↓
[Matching Engine] → 주문 체결 후 Redis에 결과 저장 + 알림 메시지를 RabbitMQ 알림 큐로 전송
↓
[Alarm Service] → 알림 큐에서 수신, FCM을 통해 사용자에게 체결 결과 전송
```

### 🚧 TODO / 향후 개선

- [ ]  실시간 호가창 데이터 제공
- [ ]  시장가 주문 처리
- [ ]  Kafka 기반 확장성 고려
- [ ]  실제 기기 대상의 FCM 송출 구현
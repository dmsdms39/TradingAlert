package com.example.TradingAlert.Service;

import com.example.TradingAlert.Dto.TradeResult;
import com.example.TradingAlert.Repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;

    // 푸시 알림 전송
    public void sendNotification(String title, TradeResult tradeResult) {
        try {
            //사용자의 토큰
            List<String> registrationTokens =  new ArrayList<>();

            userRepository.findUserById(tradeResult.getBuyUserId())
                    .ifPresent(user -> registrationTokens.add(user.getToken()));

            userRepository.findUserById(tradeResult.getSellUserId())
                    .ifPresent(user -> registrationTokens.add(user.getToken()));

            // 알림 메시지 생성
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(tradeResult.toString())
                    .build();

            // FCM 메시지 생성
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .addAllTokens(registrationTokens)
                    .build();

            // 메시지 전송
            FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);
            System.out.println("Push notification sent successfully: " + registrationTokens);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send push notification", e);
        }
    }
}
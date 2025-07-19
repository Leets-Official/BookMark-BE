package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.notification.application.dto.request.NotificationItem;
import leets.bookmark.domain.notification.application.mapper.NotificationItemMapper;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationGetService notificationGetService;

    private final KakaoNotificationService kakaoNotificationService;

    private final NotificationItemMapper notificationItemMapper;

    @Transactional
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    public void sendNotification(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = now.minusMinutes(5);
        LocalDateTime to = now.plusMinutes(10); // 오차
        List<Notification> notifications = notificationGetService.findAllToNotify(from, to);

        Map<User, List<Notification>> userNotificationMap = notifications.stream()
                .collect(Collectors.groupingBy(Notification::getUser));

        // 유저 단위로 카카오 알림 전송
        for (Map.Entry<User, List<Notification>> entry : userNotificationMap.entrySet()) {
            User user = entry.getKey();
            List<Notification> userNotifications = entry.getValue();

            List<NotificationItem> items = userNotifications.stream()
                    .map(notificationItemMapper::toNotificationItem)
                    .toList();

            kakaoNotificationService.sendListTemplate(user, items);

            userNotifications.forEach(Notification::setNotified);
        }
    }
}

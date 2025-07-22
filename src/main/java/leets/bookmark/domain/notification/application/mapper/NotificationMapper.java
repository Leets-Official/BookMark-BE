package leets.bookmark.domain.notification.application.mapper;

import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toNotification(NotificationSaveRequest request, User user, Bookmark bookmark, String fileUrl){
        return Notification.builder()
                .user(user)
                .bookmark(bookmark)
                .title(bookmark.getTitle())
                .description(bookmark.getMemo() == null ? "" : bookmark.getMemo())
                .fileUrl(fileUrl)
                .notifyAt(request.notifyAt())
                .isNotified(false)
                .build();
    }

    public NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .notifyAt(notification.getNotifyAt())
                .isNotified(notification.getIsNotified())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}

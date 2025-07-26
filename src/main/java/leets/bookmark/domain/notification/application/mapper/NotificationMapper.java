package leets.bookmark.domain.notification.application.mapper;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toNotification(NotificationSaveRequest request, User user, Bookmark bookmark){
        return Notification.builder()
                .user(user)
                .bookmark(bookmark)
                .title(bookmark.getTitle())
                .description(bookmark.getMemo())
                .fileUrl(bookmark.getFile() != null ? bookmark.getFile().getFileUrl() : null)
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

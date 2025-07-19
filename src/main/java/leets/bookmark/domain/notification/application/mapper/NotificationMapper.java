package leets.bookmark.domain.notification.application.mapper;

import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toNotification(NotificationSaveRequest request, User user, Long bookmarkId, Long fileId){
        return Notification.builder()
                .user(user)
                .bookmarkId(bookmarkId)
                .title("example")   // Todo : bookmark.getTitle().getMemo()로 수정
                .description("description") // Todo : bookmark.get로 수정
                .fileId(fileId)
                .notifyAt(request.notifyAt())
                .build();

    }

    public NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .notifyAt(notification.getNotifyAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}

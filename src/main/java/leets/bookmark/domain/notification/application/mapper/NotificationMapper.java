package leets.bookmark.domain.notification.application.mapper;

import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toNotification(NotificationSaveRequest request, User user, Long bookmarkId, String fileUrl){
        return Notification.builder()
                .user(user)
                .bookmarkId(bookmarkId)
                .title("example")   // Todo : bookmark.getTitle().getTitle()로 수정
                .description("description") // Todo : bookmark.getMemo()로 수정 (null 체크 필수)
                .fileUrl(fileUrl)   // Todo : boomark.getFile().getFileUrl()로 수정
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

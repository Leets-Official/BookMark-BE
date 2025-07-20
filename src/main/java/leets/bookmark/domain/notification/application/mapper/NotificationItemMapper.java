package leets.bookmark.domain.notification.application.mapper;

import leets.bookmark.global.auth.oauth2.application.dto.request.NotificationItemRequest;
import leets.bookmark.domain.notification.domain.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationItemMapper {

    public NotificationItemRequest toNotificationItem(Notification notification){
        return NotificationItemRequest.builder()
                .title(notification.getTitle())
                .description(notification.getDescription())
                .imageUrl(notification.getFileUrl())    // notification.getBookmark().getFile().getUrl
                .build();
    }
}

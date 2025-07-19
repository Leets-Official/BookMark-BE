package leets.bookmark.domain.notification.application.mapper;

import leets.bookmark.domain.notification.application.dto.request.NotificationItem;
import leets.bookmark.domain.notification.domain.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationItemMapper {

    public NotificationItem toNotificationItem(Notification notification){
        return NotificationItem.builder()
                .title(notification.getTitle())
                .description(notification.getDescription())
                .imageUrl(notification.getFileUrl())    // notification.getBookmark().getFile().getUrl
                .build();
    }
}

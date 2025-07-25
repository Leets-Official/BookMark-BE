package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.notification.application.dto.request.NotificationUpdateRequest;
import leets.bookmark.domain.notification.domain.entity.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationUpdateService {
    public void update(Notification notification, NotificationUpdateRequest request) {
        notification.updateNotifyAt(request.notifyAt());
        notification.setUnNotified();
    }
}

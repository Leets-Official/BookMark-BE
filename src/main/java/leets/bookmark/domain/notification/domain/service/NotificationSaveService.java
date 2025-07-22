package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationSaveService {

    private final NotificationRepository notificationRepository;

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}

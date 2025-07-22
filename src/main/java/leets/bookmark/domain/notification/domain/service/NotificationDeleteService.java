package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationDeleteService {

    private final NotificationRepository notificationRepository;

    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }
}

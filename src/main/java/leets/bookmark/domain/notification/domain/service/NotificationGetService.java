package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.notification.application.exception.NotificationNotFoundException;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationGetService {

    private final NotificationRepository notificationRepository;

    public Notification findById(long notificationId){
        return notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);
    }

    public Optional<Notification> findByBookmarkId(Long bookmarkId) {
        return notificationRepository.findByBookmarkId(bookmarkId);
    }
}

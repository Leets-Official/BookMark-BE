package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationDeleteService {

    private final NotificationRepository notificationRepository;

    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }

    public void deleteByBookmarks(List<Bookmark> bookmarks) {
        notificationRepository.deleteByBookmarkIn(bookmarks);
    }
}

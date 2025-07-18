package leets.bookmark.domain.notification.application.usecase;

import leets.bookmark.domain.notification.application.dto.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.mapper.NotificationMapper;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.service.NotificationSaveService;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationUseCase {

    private final NotificationSaveService notificationSaveService;

    private final NotificationMapper notificationMapper;

    public void saveNotification(User user, long bookmarkId, long fileId, NotificationSaveRequest request){
        Notification notification = notificationMapper.toNotification(request, user, bookmarkId, fileId);

        notificationSaveService.save(notification);
    }
}

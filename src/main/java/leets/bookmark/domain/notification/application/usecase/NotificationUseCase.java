package leets.bookmark.domain.notification.application.usecase;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.application.exception.NotificationOwnerMismatchException;
import leets.bookmark.domain.notification.application.mapper.NotificationMapper;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.service.NotificationDeleteService;
import leets.bookmark.domain.notification.domain.service.NotificationGetService;
import leets.bookmark.domain.notification.domain.service.NotificationSaveService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationUseCase {

    private final NotificationGetService notificationGetService;
    private final NotificationSaveService notificationSaveService;
    private final NotificationDeleteService notificationDeleteService;

    private final NotificationMapper notificationMapper;

    private final UserGetService userGetService;

    private final BookmarkGetService bookmarkGetService;

    public NotificationResponse getNotification(Long bookmarkId) {
        return notificationGetService.findByBookmarkId(bookmarkId)
                .map(notificationMapper::toNotificationResponse)
                .orElse(null);
    }

    public void saveNotification(User user, long bookmarkId, String fileUrl, NotificationSaveRequest request){
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        Notification notification = notificationMapper.toNotification(request, user, bookmark, fileUrl);
        notificationSaveService.save(notification);
    }

    public void deleteNotification(Long userId, long notificationId) {
        User user = userGetService.findById(userId);

        Notification notification = notificationGetService.findById(notificationId);
        validateNotificationOwner(user, notification);

        notificationDeleteService.delete(notification);
    }

    private void validateNotificationOwner(User user, Notification notification){
        if(!user.equals(notification.getUser())){
            throw new NotificationOwnerMismatchException();
        }
    }

}

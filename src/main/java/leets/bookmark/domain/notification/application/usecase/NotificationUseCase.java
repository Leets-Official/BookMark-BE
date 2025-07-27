package leets.bookmark.domain.notification.application.usecase;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.dto.request.NotificationUpdateRequest;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.application.exception.NotificationBookmarkMismatchException;
import leets.bookmark.domain.notification.application.exception.NotificationOwnerMismatchException;
import leets.bookmark.domain.notification.application.mapper.NotificationMapper;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.notification.domain.service.NotificationDeleteService;
import leets.bookmark.domain.notification.domain.service.NotificationGetService;
import leets.bookmark.domain.notification.domain.service.NotificationSaveService;
import leets.bookmark.domain.notification.domain.service.NotificationUpdateService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationUseCase {

    private final NotificationGetService notificationGetService;
    private final NotificationSaveService notificationSaveService;
    private final NotificationUpdateService notificationUpdateService;
    private final NotificationDeleteService notificationDeleteService;

    private final NotificationMapper notificationMapper;

    private final UserGetService userGetService;

    @Transactional(readOnly = true)
    public NotificationResponse getNotification(Long userId, Long bookmarkId) {
        User user = userGetService.findById(userId);
        Optional<Notification> notification = notificationGetService.findByBookmarkId(bookmarkId);

        notification.ifPresent(value -> validateNotificationOwner(user, value));
        return notification
                .map(notificationMapper::toNotificationResponse)
                .orElse(null);
    }

    @Transactional
    public void saveNotification(User user, Bookmark bookmark, NotificationSaveRequest request){
        Notification notification = notificationMapper.toNotification(request, user, bookmark,null);
        notificationSaveService.save(notification);
    }

    @Transactional
    public void updateNotification(User user, Bookmark bookmark, NotificationUpdateRequest request){

        Notification notification = notificationGetService.findById(request.notificationId());

        validateNotificationOwner(user, notification);
        validateNotificationBelongsToBookmark(notification, bookmark);

        if (request.notifyAt() != null) {
            if (!request.notifyAt().equals(notification.getNotifyAt())){
                notificationUpdateService.update(notification, request);
            }
        }
        else{
            notificationDeleteService.delete(notification);
        }
    }

    private void validateNotificationOwner(User user, Notification notification){
        if(!user.equals(notification.getUser())){
            throw new NotificationOwnerMismatchException();
        }
    }

    private void validateNotificationBelongsToBookmark(Notification notification, Bookmark bookmark){
        if (!notification.getBookmark().getId().equals(bookmark.getId())) {
            throw new NotificationBookmarkMismatchException();
        }
    }

}

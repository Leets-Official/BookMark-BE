package leets.bookmark.domain.notification.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class NotificationNotFoundException extends BusinessException {
    public NotificationNotFoundException(){
        super(NotificationErrorCode.NOTIFICATION_NOT_FOUND);
    }
}

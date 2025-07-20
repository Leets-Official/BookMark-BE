package leets.bookmark.domain.notification.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class NotificationOwnerMismatchException extends BusinessException {
    public NotificationOwnerMismatchException(){
        super(NotificationErrorCode.NOTIFICATION_OWNER_MISMATCH);
    }
}

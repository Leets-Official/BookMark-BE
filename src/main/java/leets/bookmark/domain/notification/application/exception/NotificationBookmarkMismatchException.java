package leets.bookmark.domain.notification.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class NotificationBookmarkMismatchException extends BusinessException {
    public NotificationBookmarkMismatchException(){
        super(NotificationErrorCode.NOTIFICATION_BOOKMARK_MISMATCH);
    }
}

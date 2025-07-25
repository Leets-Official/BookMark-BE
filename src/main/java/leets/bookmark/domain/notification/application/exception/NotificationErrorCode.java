package leets.bookmark.domain.notification.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

    NOTIFICATION_NOT_FOUND(404,"해당 알림이 존재하지 않습니다."),
    NOTIFICATION_OWNER_MISMATCH(403, "해당 알림 소유자만 접근 가능합니다."),
    NOTIFICATION_BOOKMARK_MISMATCH(400, "해당 Notification은 지정된 Bookmark에 속하지 않습니다.");

    private final int errorCode;
    private final String message;
}

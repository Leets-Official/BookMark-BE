package leets.bookmark.domain.notification.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationResponseMessage {
    GET_NOTIFICATION_SUCCESS("알림 조회에 성공했습니다.");

    private final String message;

}

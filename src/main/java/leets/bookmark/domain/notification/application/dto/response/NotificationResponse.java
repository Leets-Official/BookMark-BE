package leets.bookmark.domain.notification.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponse(
        Long notificationId,
        LocalDateTime notifyAt,
        boolean isNotified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

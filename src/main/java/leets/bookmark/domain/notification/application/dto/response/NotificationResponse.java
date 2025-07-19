package leets.bookmark.domain.notification.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponse(
        Long notificationId,
        LocalDateTime notifyAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

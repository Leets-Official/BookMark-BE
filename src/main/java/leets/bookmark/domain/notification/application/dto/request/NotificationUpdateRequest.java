package leets.bookmark.domain.notification.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NotificationUpdateRequest(
        @NotNull
        Long notificationId,
        LocalDateTime notifyAt
) {}

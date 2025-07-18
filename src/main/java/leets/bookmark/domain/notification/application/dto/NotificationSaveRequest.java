package leets.bookmark.domain.notification.application.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NotificationSaveRequest(
        @NotNull
        LocalDateTime notifyAt
) {}

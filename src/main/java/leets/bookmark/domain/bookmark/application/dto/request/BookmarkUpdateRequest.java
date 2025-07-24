package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;

public record BookmarkUpdateRequest(
        @NotBlank String title,
        String memo,
        @NotNull FileSaveRequest file,
        @NotNull NotificationSaveRequest notification
) {}

package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;

public record BookmarkSaveRequest(
    @NotBlank String title,
    @NotBlank String fileUrl,
    String memo,
    @NotNull FileSaveRequest file,
    @NotNull NotificationSaveRequest notification
) {}

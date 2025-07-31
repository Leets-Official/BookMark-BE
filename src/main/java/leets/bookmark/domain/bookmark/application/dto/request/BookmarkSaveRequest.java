package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.Builder;

import java.util.List;

@Builder
public record BookmarkSaveRequest(
    @NotBlank String title,
    @NotBlank String url,
    String memo,
    FileSaveRequest file,
    @NotNull NotificationSaveRequest notification,
    @NotNull String deviceType,
    @NotNull String provider,
    Long categoryId,
    List<Long> tagIds,
    Long bookmarkId,
    User user
) {}
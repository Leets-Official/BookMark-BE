package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import lombok.Builder;


import java.util.List;

@Builder
public record BookmarkSaveRequest(
    @NotBlank String title,
    @NotBlank String url,
    String memo,
    @NotBlank String thumbnailUrl,
    NotificationSaveRequest notification,
    @NotNull Platform platform,
    @NotNull Long categoryId,
    @NotNull String faviconUrl,
    @NotNull List<Long> tagIds
) {}

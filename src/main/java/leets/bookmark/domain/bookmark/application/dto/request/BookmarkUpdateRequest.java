package leets.bookmark.domain.bookmark.application.dto.request;

import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.file.application.dto.request.FileUpdateRequest;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.notification.application.dto.request.NotificationUpdateRequest;

import java.util.List;

public record BookmarkUpdateRequest(
        String title,
        String url,
        String memo,
        String thumbnailUrl,
        NotificationUpdateRequest notification,
        Platform platform,
        Long categoryId,
        String faviconUrl,
        List<Long> tagIds
) {}

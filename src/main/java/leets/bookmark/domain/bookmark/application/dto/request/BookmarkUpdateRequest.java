package leets.bookmark.domain.bookmark.application.dto.request;

import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import java.util.List;

public record BookmarkUpdateRequest(
        String title,
        String memo,
        String thumbnailUrl,
        FileSaveRequest file,
        NotificationSaveRequest notification,
        Platform platform,
        Long categoryId,
        List<Long> tagIds
) {}

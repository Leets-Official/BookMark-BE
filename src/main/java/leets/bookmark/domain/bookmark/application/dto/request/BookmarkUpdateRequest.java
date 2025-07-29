package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
import leets.bookmark.domain.bookmark.domain.entity.enums.Provider;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import java.util.List;

public record BookmarkUpdateRequest(
        @NotBlank String title,
        String memo,
        FileSaveRequest file,
        @NotNull NotificationSaveRequest notification,
        DeviceType deviceType,
        Provider provider,
        Long categoryId,
        List<Long> tagIds
) {}

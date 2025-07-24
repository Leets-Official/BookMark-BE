package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import org.springframework.web.multipart.MultipartFile;

public record BookmarkSaveRequest(
    @NotBlank String title,
    @NotBlank String url,
    String memo,
    @NotNull MultipartFile file,
    @NotNull NotificationSaveRequest notification,
    @NotNull String platform
) {}

package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record BookmarkSaveRequest(
    @NotBlank String title,
    @NotBlank String url,
    String memo,
    MultipartFile file,
    @NotNull NotificationSaveRequest notification,
    @NotNull String platform,
    Long categoryId,
    List<Long> tagIds,
    Long bookmarkId,
    User user
) {}

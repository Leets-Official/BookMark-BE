package leets.bookmark.domain.bookmark.application.dto.response;

import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BookmarkFullResponse(
        Long id,
        String url,
        String title,
        String memo,
        String platform,
        String faviconUrl,
        List<BookmarkTagInfoResponse> categoryTagInfos,
        FileResponse file,
        NotificationResponse notificationResponse,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

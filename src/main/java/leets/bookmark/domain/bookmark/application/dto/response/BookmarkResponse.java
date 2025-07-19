package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record BookmarkResponse(
        Long id,
        String url,
        String title,
        String memo,
        String thumbnailUrl,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

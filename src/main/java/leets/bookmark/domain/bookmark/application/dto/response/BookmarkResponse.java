package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BookmarkResponse(
        Long id,
        String url,
        String title,
        String memo,
        String thumbnailUrl,
        List<CategoryTagResponse> categories,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

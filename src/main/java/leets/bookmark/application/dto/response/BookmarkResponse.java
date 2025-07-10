package leets.bookmark.application.dto.response;

import java.time.LocalDateTime;

public record BookmarkResponse(
    Long id,
    String url,
    String title,
    String memo,
    String thumbnailUrl,
    String categoryName,
    LocalDateTime createdAt
) {
}

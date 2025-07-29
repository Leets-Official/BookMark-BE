package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;

@Builder
public record BookmarkPreviewResponse(
    String title,
    String thumbnailUrl,
    String faviconUrl
) {}

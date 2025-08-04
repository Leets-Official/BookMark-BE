package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;

@Builder
public record BookmarkPlatformResponse(
        String platform,
        String faviconUrl
) {
}

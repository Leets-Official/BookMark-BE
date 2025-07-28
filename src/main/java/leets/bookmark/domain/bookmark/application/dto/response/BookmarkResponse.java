package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;

@Builder
public record BookmarkResponse(
        Long id,
        String url,
        String title,
        String memo,
        String thumbnailUrl,
        List<BookmarkTagInfoResponse> categoryTagInfos,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public Bookmark toBookmark() {
        return Bookmark.builder()
                .id(id)
                .url(url)
                .title(title)
                .memo(memo)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}

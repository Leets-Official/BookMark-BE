package leets.bookmark.application.mapper;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.entity.Bookmark;

public class BookmarkMapper {

    private BookmarkMapper() {
        // Utility class
    }

    public static BookmarkResponse toResponse(Bookmark bookmark) {
        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .title(bookmark.getTitle())
                .url(bookmark.getUrl())
                .memo(bookmark.getMemo())
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
                //.categoryName(bookmark.getCategory().getName())
                .build();
    }
}

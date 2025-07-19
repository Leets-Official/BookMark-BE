package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark) {
        java.util.List<String> categoryNames = bookmark.getBookmarkCategories().stream()
                .map(category -> category.getCategoryName())
                .toList();

        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .url(bookmark.getUrl())
                .title(bookmark.getTitle())
                .memo(bookmark.getMemo())
                .thumbnailUrl(bookmark.getThumbnailUrl())
                .categoryNames(categoryNames)
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
                .build();
    }
}

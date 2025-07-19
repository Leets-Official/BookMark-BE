package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.CategoryTagResponse;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark) {
        java.util.List<CategoryTagResponse> categories = bookmark.getBookmarkCategories().stream()
                .map(category -> new CategoryTagResponse(
                        category.getCategoryName(),
                        category.getTags().stream()
                                .map(tag -> tag.getTagName())
                                .toList()
                ))
                .toList();

        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .url(bookmark.getUrl())
                .title(bookmark.getTitle())
                .memo(bookmark.getMemo())
                .thumbnailUrl(bookmark.getThumbnailUrl())
                .categories(categories)
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
                .build();
    }
}

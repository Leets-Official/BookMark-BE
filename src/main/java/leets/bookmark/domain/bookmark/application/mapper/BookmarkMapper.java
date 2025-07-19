package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.CategoryTagResponse;
import java.util.ArrayList;
import java.util.List;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark) {
        List<CategoryTagResponse> categories = new ArrayList<>();
        for (var category : bookmark.getBookmarkCategories()) {
            List<String> tagNames = category.getTags().stream()
                    .map(tag -> tag.getTagName())
                    .toList();
            categories.add(new CategoryTagResponse(category.getCategoryName(), tagNames));
        }

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

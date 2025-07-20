package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkCategoryTagResponse;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark, List<Category> categories) {
        List<BookmarkCategoryTagResponse> categoryResponses = categories.stream()
            .map(category -> {
                List<String> tagNames = category.getTags().stream()
                    .map(Tag::getTagName)
                    .toList();
                return BookmarkCategoryTagResponse.builder()
                    .categoryName(category.getCategoryName())
                    .tagNames(tagNames)
                    .build();
            })
            .toList();

        return BookmarkResponse.builder()
            .id(bookmark.getId())
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .memo(bookmark.getMemo())
            .categories(categoryResponses)
            .createdAt(bookmark.getCreatedAt())
            .updatedAt(bookmark.getUpdatedAt())
            .build();
    }
    public static BookmarkFilterRequest toFilterRequest(Long categoryId, List<String> tagNames) {
        return BookmarkFilterRequest.builder()
                .categoryId(categoryId)
                .tagNames(tagNames)
                .build();
    }
    public static BookmarkResponse toResponse(Bookmark bookmark) {
        return BookmarkResponse.builder()
            .id(bookmark.getId())
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .memo(bookmark.getMemo())
            .categories(new ArrayList<>())
            .createdAt(bookmark.getCreatedAt())
            .updatedAt(bookmark.getUpdatedAt())
            .build();
    }
}

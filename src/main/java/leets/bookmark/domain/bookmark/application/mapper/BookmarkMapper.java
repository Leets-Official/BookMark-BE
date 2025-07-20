package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkCategoryTagResponse;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.category.domain.entity.Category;

import java.util.List;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings) {
        List<String> tagIds = bookmarkTagMappings.stream()
            .map(mapping -> String.valueOf(mapping.getTag().getId()))
            .toList();

        Category category = bookmarkTagMappings.isEmpty() ? null : bookmarkTagMappings.get(0).getTag().getCategory();
        BookmarkCategoryTagResponse categoryTagResponse = BookmarkCategoryTagResponse.builder()
            .categoryName(category != null ? category.getCategoryName() : null)
            .tagId(tagIds)
            .build();

        return BookmarkResponse.builder()
            .id(bookmark.getId())
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .memo(bookmark.getMemo())
            .thumbnailUrl(null)
            .categories(List.of(categoryTagResponse))
            .createdAt(bookmark.getCreatedAt())
            .updatedAt(bookmark.getUpdatedAt())
            .build();
    }

    public static BookmarkResponse toResponseWithTags(Bookmark bookmark, List<Tag> tags) {
        List<String> tagIds = tags.stream()
            .map(tag -> String.valueOf(tag.getId()))
            .toList();

        Category category = tags.isEmpty() ? null : tags.get(0).getCategory();
        BookmarkCategoryTagResponse categoryTagResponse = BookmarkCategoryTagResponse.builder()
            .categoryName(category != null ? category.getCategoryName() : null)
            .tagId(tagIds)
            .build();

        return BookmarkResponse.builder()
            .id(bookmark.getId())
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .memo(bookmark.getMemo())
            .thumbnailUrl(null)
            .categories(List.of(categoryTagResponse))
            .createdAt(bookmark.getCreatedAt())
            .updatedAt(bookmark.getUpdatedAt())
            .build();
    }

    public static BookmarkFilterRequest toFilterRequest(Long categoryId, List<Long> tagId) {
        return BookmarkFilterRequest.builder()
            .categoryId(categoryId)
            .tagId(tagId)
            .build();
    }
}

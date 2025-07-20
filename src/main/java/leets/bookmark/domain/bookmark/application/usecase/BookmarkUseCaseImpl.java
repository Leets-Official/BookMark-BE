package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkCategoryTagResponse;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.tag.domain.entity.Tag;

import java.util.List;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings) {
        List<String> tagIds = bookmarkTagMappings.stream()
            .map(mapping -> String.valueOf(mapping.getTag().getId()))
            .toList();

        return buildBookmarkResponse(bookmark, tagIds);
    }

    public static BookmarkResponse toResponseWithTags(Bookmark bookmark, List<Tag> tags) {
        List<String> tagIds = tags.stream()
            .map(tag -> String.valueOf(tag.getId()))
            .toList();

        return buildBookmarkResponse(bookmark, tagIds);
    }

    private static BookmarkResponse buildBookmarkResponse(Bookmark bookmark, List<String> tagIds) {
        BookmarkCategoryTagResponse categoryTagResponse = BookmarkCategoryTagResponse.builder()
            .categoryName(bookmark.getCategory().getCategoryName())
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

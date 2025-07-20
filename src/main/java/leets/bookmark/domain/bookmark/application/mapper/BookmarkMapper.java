package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkTagInfoResponse;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.category.domain.entity.Category;

import java.util.List;

public class BookmarkMapper {

public static BookmarkResponse toResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings) {
    List<Long> tagIds = bookmarkTagMappings.stream()
        .map(mapping -> mapping.getTag().getId())
        .toList();

    Category category = bookmarkTagMappings.stream()
        .findFirst()
        .map(mapping -> mapping.getTag().getCategory())
        .orElse(null);

    BookmarkTagInfoResponse tagInfo = BookmarkTagInfoResponse.builder()
        .categoryName(category != null ? category.getCategoryName() : null)
        .tagId(tagIds)
        .build();

    return BookmarkResponse.builder()
        .id(bookmark.getId())
        .url(bookmark.getUrl())
        .title(bookmark.getTitle())
        .memo(bookmark.getMemo())
        .thumbnailUrl(bookmark.getFile() != null ? bookmark.getFile().getFileUrl() : null)
        .categoryTagInfos(List.of(tagInfo))
        .createdAt(bookmark.getCreatedAt())
        .updatedAt(bookmark.getUpdatedAt())
        .build();
    }

    public static BookmarkResponse toResponseWithTags(Bookmark bookmark, List<Tag> tags) {
        List<Long> tagIds = tags.stream()
            .map(Tag::getId)
            .toList();

        Category category = tags.isEmpty() ? null : tags.get(0).getCategory();
        BookmarkTagInfoResponse categoryTagResponse = BookmarkTagInfoResponse.builder()
            .categoryName(category != null ? category.getCategoryName() : null)
            .tagId(tagIds)
            .build();

        return BookmarkResponse.builder()
            .id(bookmark.getId())
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .memo(bookmark.getMemo())
            .thumbnailUrl(bookmark.getFile() != null ? bookmark.getFile().getFileUrl() : null)
            .categoryTagInfos(List.of(categoryTagResponse))
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

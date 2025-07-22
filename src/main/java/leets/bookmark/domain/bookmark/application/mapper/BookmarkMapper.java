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
        BookmarkTagInfoResponse tagInfo = toBookmarkTagInfoResponseFromMappings(bookmarkTagMappings);

        return buildBookmarkResponse(bookmark, tagInfo);
    }

    public static BookmarkResponse toResponseWithTags(Bookmark bookmark, List<Tag> tags) {
        BookmarkTagInfoResponse categoryTagResponse = toBookmarkTagInfoResponseFromTags(tags);

        return buildBookmarkResponse(bookmark, categoryTagResponse);
    }

    public static BookmarkFilterRequest toFilterRequest(Long categoryId, List<Long> tagId) {
        return BookmarkFilterRequest.builder()
            .categoryId(categoryId)
            .tagId(tagId)
            .build();
    }

    private static BookmarkTagInfoResponse toBookmarkTagInfoResponseFromMappings(List<BookmarkTagMapping> bookmarkTagMappings) {
        List<BookmarkTagInfoResponse.TagInfo> tags = bookmarkTagMappings.stream()
            .map(mapping -> {
                Tag tag = mapping.getTag();
                return BookmarkTagInfoResponse.TagInfo.builder()
                    .tagId(tag.getId())
                    .tagName(tag.getTagName())
                    .build();
            })
            .toList();

        Category category = bookmarkTagMappings.stream()
            .findFirst()
            .map(mapping -> mapping.getTag().getCategory())
            .orElse(null);

        return buildBookmarkTagInfoResponse(category, tags);
    }

    private static BookmarkTagInfoResponse toBookmarkTagInfoResponseFromTags(List<Tag> tags) {
        List<BookmarkTagInfoResponse.TagInfo> tagInfos = tags.stream()
            .map(tag -> BookmarkTagInfoResponse.TagInfo.builder()
                .tagId(tag.getId())
                .tagName(tag.getTagName())
                .build())
            .toList();

        Category category = tags.isEmpty() ? null : tags.get(0).getCategory();

        return buildBookmarkTagInfoResponse(category, tagInfos);
    }

    private static BookmarkTagInfoResponse buildBookmarkTagInfoResponse(Category category, List<BookmarkTagInfoResponse.TagInfo> tags) {
        return BookmarkTagInfoResponse.builder()
            .categoryId(category != null ? category.getId() : null)
            .categoryName(category != null ? category.getCategoryName() : null)
            .tags(tags)
            .build();
    }

    private static BookmarkResponse buildBookmarkResponse(Bookmark bookmark, BookmarkTagInfoResponse tagInfo) {
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
}

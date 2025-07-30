package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkTagInfoResponse;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.category.domain.entity.Category;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkMapper {

    public BookmarkResponse toResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings, File file) {
        BookmarkTagInfoResponse tagInfo = toBookmarkTagInfoResponseFromMappings(bookmarkTagMappings);

        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .url(bookmark.getUrl())
                .title(bookmark.getTitle())
                .memo(bookmark.getMemo())
                .thumbnailUrl(file != null ? file.getFileUrl() : null)
                .categoryTagInfos(List.of(tagInfo))
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
                .build();
    }

    public BookmarkFilterRequest toFilterRequest(Long categoryId, List<Long> tagId) {
        return BookmarkFilterRequest.builder()
            .categoryId(categoryId)
            .tagId(tagId)
            .build();
    }

    private BookmarkTagInfoResponse toBookmarkTagInfoResponseFromMappings(List<BookmarkTagMapping> bookmarkTagMappings) {
        if (bookmarkTagMappings.isEmpty()) {
            return BookmarkTagInfoResponse.builder()
                .categoryId(null)
                .categoryName(null)
                .tags(List.of())
                .build();
        }

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

    private BookmarkTagInfoResponse toBookmarkTagInfoResponseFromTags(List<Tag> tags) {
        List<BookmarkTagInfoResponse.TagInfo> tagInfos = tags.stream()
            .map(tag -> BookmarkTagInfoResponse.TagInfo.builder()
                .tagId(tag.getId())
                .tagName(tag.getTagName())
                .build())
            .toList();

        Category category = tags.isEmpty() ? null : tags.get(0).getCategory();

        return buildBookmarkTagInfoResponse(category, tagInfos);
    }

    private BookmarkTagInfoResponse buildBookmarkTagInfoResponse(Category category, List<BookmarkTagInfoResponse.TagInfo> tags) {
        return BookmarkTagInfoResponse.builder()
            .categoryId(category != null ? category.getId() : null)
            .categoryName(category != null ? category.getCategoryName() : null)
            .tags(tags)
            .build();
    }
}

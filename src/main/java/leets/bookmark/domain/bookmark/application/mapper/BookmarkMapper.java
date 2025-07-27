package leets.bookmark.domain.bookmark.application.mapper;
import leets.bookmark.domain.file.domain.entity.File;
import org.springframework.web.multipart.MultipartFile;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkTagInfoResponse;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.category.domain.entity.Category;

import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkMapper {

    public BookmarkResponse toResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings) {
        BookmarkTagInfoResponse tagInfo = toBookmarkTagInfoResponseFromMappings(bookmarkTagMappings);

        return buildBookmarkResponse(bookmark, tagInfo);
    }

    public BookmarkResponse toResponseWithTags(Bookmark bookmark, List<Tag> tags) {
        BookmarkTagInfoResponse categoryTagResponse = toBookmarkTagInfoResponseFromTags(tags);

        return buildBookmarkResponse(bookmark, categoryTagResponse);
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

    private BookmarkResponse buildBookmarkResponse(Bookmark bookmark, BookmarkTagInfoResponse tagInfo) {
        return BookmarkResponse.builder()
            .id(bookmark.getId())
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .memo(bookmark.getMemo())
            .thumbnailUrl(bookmark.getThumbnailUrl())
            .categoryTagInfos(List.of(tagInfo))
            .createdAt(bookmark.getCreatedAt())
            .updatedAt(bookmark.getUpdatedAt())
            .build();
    }

    public Bookmark toBookmark(Long userId, BookmarkSaveRequest request) {
        return Bookmark.builder()
                .user(User.builder().id(userId).build())
                .title(request.title())
                .url(request.url())
                .memo(request.memo())
                .platform(request.platform())
                .build();
    }

    public BookmarkSaveRequest toSaveRequestWithFile(BookmarkSaveRequest request, MultipartFile file) {
        return BookmarkSaveRequest.builder()
            .title(request.title())
            .url(request.url())
            .memo(request.memo())
            .file(file)
            .notification(request.notification())
            .platform(request.platform())
            .build();
    }
}

package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkFullResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkTagInfoResponse;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.application.dto.request.NotificationUpdateRequest;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.category.domain.entity.Category;

import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;

@Component
@RequiredArgsConstructor
public class BookmarkMapper {

    public BookmarkResponse toResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings,
                                       FileResponse fileResponse) {
        BookmarkTagInfoResponse tagInfo = toBookmarkTagInfoResponseFromMappings(bookmarkTagMappings);

        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .url(bookmark.getUrl())
                .title(bookmark.getTitle())
                .memo(bookmark.getMemo())
                .platform(String.valueOf(bookmark.getPlatform()))
                .faviconUrl(bookmark.getFaviconUrl())
                .categoryTagInfos(List.of(tagInfo))
                .file(fileResponse)
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
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
            .platform(bookmark.getPlatform() != null ? bookmark.getPlatform().name() : null)
            .categoryTagInfos(List.of(tagInfo))
            .createdAt(bookmark.getCreatedAt())
            .updatedAt(bookmark.getUpdatedAt())
            .build();
    }

    public Bookmark toBookmark(User user, BookmarkSaveRequest request, Category category) {
        return Bookmark.builder()
            .user(user)
            .category(category)
            .title(request.title())
            .url(request.url())
            .memo(request.memo())
            .platform(request.platform())
            .faviconUrl(request.faviconUrl())
            .build();
    }


    public BookmarkTagMapping toMapping(Bookmark bookmark, Tag tag) {
        return BookmarkTagMapping.builder()
            .bookmark(bookmark)
            .tag(tag)
            .build();
    }

    public List<BookmarkTagMapping> toMappings(Bookmark bookmark, List<Tag> tags) {
        return tags.stream()
            .map(tag -> toMapping(bookmark, tag))
            .toList();
    }

    public BookmarkFullResponse toFullResponse(Bookmark bookmark, List<BookmarkTagMapping> bookmarkTagMappings,
                                           FileResponse fileResponse, NotificationResponse notificationResponse) {

        BookmarkTagInfoResponse tagInfo = toBookmarkTagInfoResponseFromMappings(bookmarkTagMappings);

        return BookmarkFullResponse.builder()
                .id(bookmark.getId())
                .url(bookmark.getUrl())
                .title(bookmark.getTitle())
                .memo(bookmark.getMemo())
                .platform(String.valueOf(bookmark.getPlatform()))
                .faviconUrl(bookmark.getFaviconUrl())
                .categoryTagInfos(List.of(tagInfo))
                .file(fileResponse)
                .notificationResponse(notificationResponse)
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
                .build();
    }

    public NotificationSaveRequest toNotificationSaveRequest(NotificationUpdateRequest updateRequest) {
        return new NotificationSaveRequest(updateRequest.notifyAt());
    }
}

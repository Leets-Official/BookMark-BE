package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPlatformResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookmarkPlatformMapper {

    public BookmarkPlatformResponse toBookmarkPlatformResponse(Bookmark bookmark) {
        return BookmarkPlatformResponse.builder()
                .platform(String.valueOf(bookmark.getPlatform().getPlatformName()))
                .faviconUrl(bookmark.getFaviconUrl())
                .build();
    }

    public List<BookmarkPlatformResponse> toBookmarkPlatformResponseList(List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(this::toBookmarkPlatformResponse)
                .distinct()
                .toList();
    }

    public List<Bookmark> toBookmarkPlatformList(List<Bookmark> bookmarks) {
        Map<Platform, Bookmark> platforms = new EnumMap<>(Platform.class);

        for (Bookmark bookmark : bookmarks) {
            platforms.putIfAbsent(bookmark.getPlatform(), bookmark);
        }

        return new ArrayList<>(platforms.values());
    }
}

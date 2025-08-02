package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.file.domain.entity.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookmarkCategoryMapper {

    private final BookmarkGetService bookmarkGetService;

    public Map<Long, List<String>> toThumbnailMap(List<Category> categories) {
        Map<Long, List<String>> thumbnailMap = new HashMap<>();

        for (Category category : categories) {
            List<Bookmark> bookmarks = bookmarkGetService.getRecent3BookmarksByCategory(category);

            List<String> thumbnailUrls = bookmarks.stream()
                    .map(Bookmark::getFile)
                    .map(File::getFileUrl)
                    .toList();

            thumbnailMap.put(category.getId(), thumbnailUrls);
        }

        return thumbnailMap;
    }

    public Map<Long, List<Platform>> toPlatformMap(List<Category> categories) {
        Map<Long, List<Platform>> platformMap = new HashMap<>();

        for (Category category : categories) {
            List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByCategory(category);

            List<Platform> platforms = bookmarks.stream()
                    .map(Bookmark::getPlatform)
                    .distinct()
                    .toList();

            platformMap.put(category.getId(), platforms);
        }

        return platformMap;
    }
}

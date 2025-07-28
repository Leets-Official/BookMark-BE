package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Slice;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;

    public List<Bookmark> getBookmarksByMemoContaining(String keyword, Long userId) {
        return bookmarkRepository.findByMemoContainingAndUserId(keyword, userId);
    }

    public List<BookmarkTagMapping> getMappingsByBookmark(Bookmark bookmark) {
        return bookmarkTagMappingRepository.findAllByBookmarkId(bookmark.getId());
    }

    public List<Bookmark> getBookmarksByCategoryIncludingUntagged(Long userId, Long categoryId, String platform) {
        return bookmarkRepository.findAllByUserIdAndCategoryId(userId, categoryId, platform);
    }

    public List<Bookmark> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        Long categoryId = request.categoryId();
        List<Long> tagIds = request.tagId();
        String platform = request.platform();

        if (tagIds == null || tagIds.isEmpty()) {
            return bookmarkRepository.findAllByUserIdAndCategoryId(userId, categoryId, platform);
        }
        return bookmarkRepository.findAllWithFilter(userId, categoryId, tagIds, platform);
    }

    public List<Bookmark> getAllBookmarks(Long userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }

    public Bookmark getBookmarkById(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
                .orElseThrow(BookmarkNotFoundException::new);
    }

    public Slice<Bookmark> getRecentBookmarksByPlatform(Long userId, String platform, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndPlatformOrderByCreatedAtDesc(userId, platform, pageable);
    }

    public Slice<Bookmark> getBookmarksByPlatformWithSlice(Long userId, String platform, Long lastBookmarkId, Pageable pageable) {
        if (lastBookmarkId == null) {
            return bookmarkRepository.findTopByUserIdAndPlatformOrderByIdDesc(userId, platform, pageable);
        } else {
            return bookmarkRepository.findByUserIdAndPlatformAndIdLessThanOrderByIdDesc(
                userId, platform, lastBookmarkId, pageable);
        }
    }

    public Slice<Bookmark> getSavedBookmarksByPlatform(Long userId, String platform, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndPlatformAndIsSavedTrue(userId, platform, pageable);
    }
}

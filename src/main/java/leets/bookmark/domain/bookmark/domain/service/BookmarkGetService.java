package leets.bookmark.domain.bookmark.domain.service;
import leets.bookmark.domain.bookmark.application.exception.InvalidDeviceTypeException;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
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

    public List<Bookmark> getBookmarksByCategoryIncludingUntagged(Long userId, List<Long> categoryIds, DeviceType deviceType) {
        return bookmarkRepository.findAllByUserIdAndCategoryIds(userId, categoryIds, deviceType);
    }

    public List<Bookmark> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        List<Long> categoryIds = request.categoryIds();
        List<Long> tagIds = request.tagId();
        DeviceType deviceType = request.deviceType();

        if (deviceType == null) {
            throw new InvalidDeviceTypeException();
        }

        if (tagIds == null || tagIds.isEmpty()) {
            return bookmarkRepository.findAllByUserIdAndCategoryIds(userId, categoryIds, deviceType);
        }
        return bookmarkRepository.findAllWithFilter(userId, categoryIds, tagIds, deviceType);
    }

    public List<Bookmark> getAllBookmarks(Long userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }

    public Bookmark getBookmarkById(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
                .orElseThrow(BookmarkNotFoundException::new);
    }

    public Slice<Bookmark> getRecentBookmarksByPlatform(Long userId, DeviceType deviceType, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndPlatformOrderByCreatedAtDesc(userId, deviceType, pageable);
    }

    public Slice<Bookmark> getBookmarksByPlatformWithSlice(Long userId, DeviceType deviceType, Long lastBookmarkId, Pageable pageable) {
        if (lastBookmarkId == null) {
            return bookmarkRepository.findTopByUserIdAndPlatformOrderByIdDesc(userId, deviceType, pageable);
        } else {
            return bookmarkRepository.findByUserIdAndPlatformAndIdLessThanOrderByIdDesc(
                userId, deviceType, lastBookmarkId, pageable);
        }
    }

    public Slice<Bookmark> getSavedBookmarksByPlatform(Long userId, DeviceType deviceType, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndPlatformAndIsSavedTrue(userId, deviceType, pageable);
    }
}

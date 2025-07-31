package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
import leets.bookmark.domain.bookmark.domain.entity.enums.Provider;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.user.domain.entity.User;
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

    public List<Bookmark> getBookmarksByMemoContaining(String keyword, User user) {
        return bookmarkRepository.findByMemoContainingAndUserId(keyword, user.getId());
    }

    public List<BookmarkTagMapping> getMappingsByBookmark(Bookmark bookmark) {
        return bookmarkTagMappingRepository.findAllByBookmarkId(bookmark.getId());
    }

    public List<Bookmark> getAllBookmarks(leets.bookmark.domain.user.domain.entity.User user) {
        return bookmarkRepository.findAllByUserId(user.getId());
    }

    public Bookmark getBookmarkById(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
            .orElseThrow(BookmarkNotFoundException::new);
    }

    public Slice<Bookmark> getRecentBookmarksByPlatform(User user, DeviceType deviceType, Provider provider, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndDeviceTypeAndProviderOrderByCreatedAtDesc(user.getId(), deviceType, provider,pageable);
    }

    public Slice<Bookmark> getBookmarksByPlatformWithSlice(User user, DeviceType deviceType, Long lastBookmarkId, Pageable pageable) {
        if (lastBookmarkId == null) {
            return bookmarkRepository.findTopByUserIdAndDeviceTypeOrderByIdDesc(user.getId(), deviceType, pageable);
        } else {
            return bookmarkRepository.findByUserIdAndDeviceTypeAndIdLessThanOrderByIdDesc(
                user.getId(), deviceType, lastBookmarkId, pageable);
        }
    }

    public Slice<Bookmark> getSavedBookmarksByPlatform(User user, DeviceType deviceType, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndDeviceTypeAndIsSavedTrue(user.getId(), deviceType, pageable);
    }
}

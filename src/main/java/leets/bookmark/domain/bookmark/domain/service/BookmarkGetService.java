package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
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

    public Slice<Bookmark> getRecentBookmarks(User user, Platform platform, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndPlatformOrderByCreatedAtDesc(user.getId(), platform, pageable);
    }

    public Slice<Bookmark> getSavedBookmarks(User user, Platform platform, Pageable pageable) {
        return bookmarkRepository.findByUserIdAndPlatformOrderByCreatedAtDesc(user.getId(), platform, pageable);
    }
}

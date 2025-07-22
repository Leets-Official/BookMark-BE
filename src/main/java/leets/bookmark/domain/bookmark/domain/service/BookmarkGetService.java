package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Bookmark> getBookmarksByCategoryIncludingUntagged(Long userId, Long categoryId) {
        return bookmarkRepository.findAllByUserIdAndCategoryId(userId, categoryId);
    }

    public List<Bookmark> getFilteredBookmarks(Long userId, Long categoryId, List<Long> tagIds) {
        return bookmarkRepository.findAllWithFilter(userId, categoryId, tagIds);
    }

    public List<Bookmark> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        Long categoryId = request.categoryId();
        List<Long> tagIds = request.tagId();

        if (tagIds == null || tagIds.isEmpty()) {
            return bookmarkRepository.findAllByUserIdAndCategoryId(userId, categoryId);
        }
        return bookmarkRepository.findAllWithFilter(userId, categoryId, tagIds);
    }

    public List<Bookmark> getAllBookmarks(Long userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }
}

package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkPlatformMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;
    private final BookmarkPlatformMapper bookmarkPlatformMapper;

    public List<BookmarkTagMapping> getMappingsByBookmark(Bookmark bookmark) {
        return bookmarkTagMappingRepository.findAllByBookmarkId(bookmark.getId());
    }

    public List<BookmarkTagMapping> getBookmarksByTag(Tag tag) {
        return bookmarkTagMappingRepository.findAllByTag(tag);
    }

    public Slice<Bookmark> search(Long userId, BookmarkSearchCondition condition, Pageable pageable) {
        return bookmarkRepository.searchWithFilters(userId, condition, pageable);
    }

    public Bookmark getBookmarkById(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
                .orElseThrow(BookmarkNotFoundException::new);
    }

    public List<Bookmark> getRecent3BookmarksByCategory(Category category) {
        return bookmarkRepository.findTop3ByCategoryOrderByCreatedAtDesc(category);
    }

    public List<Bookmark> getBookmarksByCategory(Category category) {
        return bookmarkRepository.findAllByCategory(category);
    }

    public List<Bookmark> getDistinctPlatformsByUser(User user) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserOrderByCreatedAtDesc(user);
        return bookmarkPlatformMapper.toBookmarkPlatformList(bookmarks);
    }
}

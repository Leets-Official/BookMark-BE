package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
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

    public List<BookmarkTagMapping> getMappingsByBookmark(Bookmark bookmark) {
        return bookmarkTagMappingRepository.findAllByBookmarkId(bookmark.getId());
    }

    public Slice<Bookmark> search(Long userId, BookmarkSearchCondition condition, Pageable pageable) {
        return bookmarkRepository.searchWithFilters(userId, condition, pageable);
    }

    public Bookmark getBookmarkById(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
                .orElseThrow(BookmarkNotFoundException::new);
    }
}

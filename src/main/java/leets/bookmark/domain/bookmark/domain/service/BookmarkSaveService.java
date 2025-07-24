package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkSaveService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;

    public void save(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    public void save(BookmarkSaveRequest request, Long userId) {
        Bookmark bookmark = bookmarkMapper.toBookmark(userId,request);
        bookmarkRepository.save(bookmark);
    }
}

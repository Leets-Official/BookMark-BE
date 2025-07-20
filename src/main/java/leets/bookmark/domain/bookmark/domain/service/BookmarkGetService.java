package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarksByMemoContaining(String keyword, Long userId) {
        return bookmarkRepository.findByMemoContainingAndUserId(keyword, userId);
    }
}

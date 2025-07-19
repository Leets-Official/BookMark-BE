package leets.bookmark.domain.bookmark.service;

import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarksAllByMemo(String keyword) {
        return bookmarkRepository.findByMemoContaining(keyword);
    }
}

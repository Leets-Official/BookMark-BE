
package leets.bookmark.domain.service;

import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetServiceImpl implements BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<Bookmark> getBookmarksByMemo(String keyword) {
        return bookmarkRepository.findByMemoContaining(keyword);
    }
}

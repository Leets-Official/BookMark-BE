package leets.bookmark.domain.bookmark.domain.service;


import jakarta.transaction.Transactional;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void delete(final Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }

}

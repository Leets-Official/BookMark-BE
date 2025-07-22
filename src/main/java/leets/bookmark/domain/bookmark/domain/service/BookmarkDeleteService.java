package leets.bookmark.domain.bookmark.domain.service;


import leets.bookmark.domain.bookmark.application.exception.BookmarkNotFoundException;
import leets.bookmark.domain.bookmark.application.exception.NoBookmarkPermissionException;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {

    private final BookmarkRepository bookmarkRepository;

    public void delete(final Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }

    public void deleteByUserIdAndBookmarkId(final Long userId, final Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(BookmarkNotFoundException::new);

        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }

        delete(bookmark);
    }

}

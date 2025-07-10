package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkPreviewMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.application.exeception.BookMarkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBookmarkPreviewUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkPreviewMapper bookmarkPreviewMapper;

    public BookmarkPreviewResponse execute(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
            .orElseThrow(BookMarkNotFoundException::new);

        return bookmarkPreviewMapper.toResponse(bookmark);
    }
}

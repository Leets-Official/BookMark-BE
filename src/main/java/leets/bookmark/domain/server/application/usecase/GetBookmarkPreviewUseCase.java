package leets.bookmark.domain.server.application.usecase;

import leets.bookmark.domain.server.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.server.domain.entity.Bookmark;
import leets.bookmark.domain.server.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBookmarkPreviewUseCase {

    private final BookmarkRepository bookmarkRepository;

    public GetBookmarkPreviewUseCase(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public BookmarkPreviewResponse execute(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId);

        if (bookmark == null) {
            throw new IllegalArgumentException("존재하지 않는 북마크입니다.");
        }

        return new BookmarkPreviewResponse(
                bookmark.getTitle(),
                bookmark.getThumbnailUrl()
        );
    }
}

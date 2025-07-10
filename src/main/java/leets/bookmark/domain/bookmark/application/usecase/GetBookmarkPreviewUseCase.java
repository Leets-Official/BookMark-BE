package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBookmarkPreviewUseCase {

    private final BookmarkRepository bookmarkRepository;

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

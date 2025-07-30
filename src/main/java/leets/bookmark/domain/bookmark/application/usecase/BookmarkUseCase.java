package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BookmarkUseCase {
    List<BookmarkResponse> getByMemoContaining(Long userId, String keyword);
    Slice<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkSearchRequest request);
    List<BookmarkResponse> getAllBookmarks(Long userId);
    List<BookmarkResponse> getFilteredBookmarksByCategory(Long userId, Long categoryId);
    List<BookmarkResponse> extractPreviewFromUrl(String url);
}

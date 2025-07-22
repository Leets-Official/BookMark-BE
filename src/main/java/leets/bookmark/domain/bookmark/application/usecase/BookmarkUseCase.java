package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.tag.domain.entity.Tag;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.global.common.response.CommonResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BookmarkUseCase {
    List<BookmarkResponse> getByMemoContaining(Long userId, String keyword);
    List<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkFilterRequest request);
    List<BookmarkResponse> getAllBookmarks(Long userId);
    List<BookmarkResponse> getFilteredBookmarksByCategory(Long userId, Long categoryId);
}

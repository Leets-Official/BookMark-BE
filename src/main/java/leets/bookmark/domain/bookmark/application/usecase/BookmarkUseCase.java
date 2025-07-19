package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.global.common.response.CommonResponse;

import java.util.List;

public interface BookmarkUseCase {
    List<BookmarkResponse> getByMemoContaining(String keyword);
    List<BookmarkResponse> getFilteredBookmarks(BookmarkFilterRequest request);
    List<BookmarkResponse> getAllBookmarks();
}

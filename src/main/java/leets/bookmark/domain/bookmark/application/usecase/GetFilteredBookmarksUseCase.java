package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;

import java.util.List;

public interface GetFilteredBookmarksUseCase {
    List<BookmarkResponse> getFilteredBookmarks(BookmarkFilterRequest request);
}

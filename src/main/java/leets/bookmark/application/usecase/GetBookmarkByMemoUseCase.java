package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.response.BookmarkResponse;
import java.util.List;

public interface GetBookmarkByMemoUseCase {
    List<BookmarkResponse> searchBookmarksByMemo(String keyword);
}

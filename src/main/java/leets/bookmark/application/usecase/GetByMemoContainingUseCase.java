package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.response.BookmarkResponse;
import java.util.List;

public interface GetByMemoContainingUseCase {
    List<BookmarkResponse> GetByMemoContaining(String keyword);
}

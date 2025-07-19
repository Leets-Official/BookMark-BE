package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.global.common.response.CommonResponse;
import java.util.List;

public interface GetByMemoContainingUseCase {
    CommonResponse<List<BookmarkResponse>> getByMemoContaining(String keyword);
}

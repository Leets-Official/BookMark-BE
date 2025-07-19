package leets.bookmark.application.usecase;

import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.global.common.response.CommonResponse;
import java.util.List;

public interface GetByMemoContainingUseCase {
    CommonResponse<List<BookmarkResponse>> getByMemoContaining(String keyword);
}

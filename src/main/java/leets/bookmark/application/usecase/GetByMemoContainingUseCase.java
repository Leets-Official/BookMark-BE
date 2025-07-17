package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.global.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GetByMemoContainingUseCase {
    CommonResponse<List<BookmarkResponse>> getByMemoContaining(String keyword);
}

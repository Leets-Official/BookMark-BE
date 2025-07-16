package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetByMemoContainingUseCaseImpl implements GetByMemoContainingUseCase {

    private final BookmarkGetService bookmarkGetService;

    @Override
    public ResponseEntity<CommonResponse<List<BookmarkResponse>>> GetByMemoContaining(String keyword) {
        ResponseEntity<CommonResponse<List<BookmarkResponse>>> response = bookmarkGetService.getBookmarksAllByMemo(keyword);
        List<BookmarkResponse> responses = response.getBody().getData();
        CommonResponse<List<BookmarkResponse>> body = CommonResponse.createSuccess("조회에 성공하였습니다.", responses);
        return ResponseEntity.ok(body);
    }
}

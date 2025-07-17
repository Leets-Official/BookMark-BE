package leets.bookmark.presentation.Controller;

import leets.bookmark.global.common.response.CommonResponse;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.application.usecase.GetByMemoContainingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("api/vi/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final GetByMemoContainingUseCase getByMemoContainingUseCase;

    @GetMapping("/search")
    @Operation(summary = "북마크 메모 검색 API", description = "키워드를 포함하는 메모를 가진 북마크 목록을 조회합니다.")
    public CommonResponse<List<BookmarkResponse>> searchBookmarksByMemo(@RequestParam String keyword) {
        return getByMemoContainingUseCase.getByMemoContaining(keyword);
    }
}

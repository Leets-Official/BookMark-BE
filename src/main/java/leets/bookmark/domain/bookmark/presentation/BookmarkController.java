package leets.bookmark.domain.bookmark.presentation;

import leets.bookmark.global.common.response.CommonResponse;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.usecase.GetByMemoContainingUseCase;
import leets.bookmark.domain.bookmark.application.usecase.GetFilteredBookmarksUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final GetByMemoContainingUseCase getByMemoContainingUseCase;
    private final GetFilteredBookmarksUseCase getFilteredBookmarksUseCase;

    @GetMapping("/search")
    @Operation(summary = "북마크 메모 검색 API", description = "키워드를 포함하는 메모를 가진 북마크 목록을 조회합니다.")
    public CommonResponse<List<BookmarkResponse>> searchBookmarksByMemo(@RequestParam String keyword) {
        return getByMemoContainingUseCase.getByMemoContaining(keyword);
    }

    @GetMapping
    @Operation(summary = "북마크 필터링 API", description = "카테고리 ID 또는 태그 이름 목록으로 북마크를 필터링합니다.")
    public CommonResponse<List<BookmarkResponse>> getFilteredBookmarks(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) List<String> tagNames
    ) {
        BookmarkFilterRequest request = new BookmarkFilterRequest(categoryId, tagNames);
        List<BookmarkResponse> result = getFilteredBookmarksUseCase.getFilteredBookmarks(request);
        return CommonResponse.createSuccess(BookmarkResponseMessage.BOOKMARK_FILTER_SUCCESS.getMessage(), result);
    }
}

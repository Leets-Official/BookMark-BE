package leets.bookmark.domain.bookmark.presentation;

import static leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage.*;

import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.application.usecase.BookmarkUseCase;
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

    private final BookmarkUseCase bookmarkUseCase;

    @GetMapping("/search")
    @Operation(summary = "북마크 메모 검색 API", description = "키워드를 포함하는 메모를 가진 북마크 목록을 조회합니다.")
    public CommonResponse<List<BookmarkResponse>> searchBookmarksByMemo(@CurrentUser Long userId, @RequestParam String keyword) {
        List<BookmarkResponse> result = bookmarkUseCase.getByMemoContaining(userId, keyword);
        return CommonResponse.createSuccess(BOOKMARK_MEMO_SEARCH_SUCCESS.getMessage(), result);
    }

    @GetMapping
    @Operation(summary = "북마크 필터링 API", description = "카테고리 ID 또는 태그 이름 목록으로 북마크를 필터링합니다.")
    public CommonResponse<List<BookmarkResponse>> getFilteredBookmarks(
            @CurrentUser Long userId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> tagNames
    ) {
        BookmarkFilterRequest request = BookmarkMapper.toFilterRequest(categoryId, tagNames);
        List<BookmarkResponse> result = bookmarkUseCase.getFilteredBookmarks(userId, request);
        return CommonResponse.createSuccess(BOOKMARK_FILTER_SUCCESS.getMessage(), result);
    }
    @GetMapping("/all")
    @Operation(summary = "전체 북마크 조회 API", description = "모든 북마크를 조회합니다.")
    public CommonResponse<List<BookmarkResponse>> getAllBookmarks(@CurrentUser Long userId) {
        List<BookmarkResponse> result = bookmarkUseCase.getAllBookmarks(userId);
        return CommonResponse.createSuccess(BOOKMARK_SEARCH_SUCCESS.getMessage(), result);
    }
}

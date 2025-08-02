package leets.bookmark.domain.bookmark.presentation;

import static leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage.*;

import jakarta.validation.Valid;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.usecase.BookmarkUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkUseCase bookmarkUseCase;
    private final NotificationUseCase notificationUseCase;

    @PostMapping("/search")
    @Operation(summary = "북마크 필터링 API")
    public CommonResponse<Slice<BookmarkResponse>> getFilteredBookmarks(@CurrentUser Long userId,
                                                                        @RequestBody @Valid BookmarkSearchRequest bookmarkSearchRequest) {
        Slice<BookmarkResponse> responses = bookmarkUseCase.getFilteredBookmarks(userId, bookmarkSearchRequest);
        return CommonResponse.createSuccess(BOOKMARK_FILTER_SUCCESS.getMessage(), responses);
    }

    @PostMapping()
    @Operation(summary = "북마크 저장 API", description = "알림 정보와 함께 북마크를 저장할 수 있는 API입니다.")
    public CommonResponse<Void> saveBookmark(
        @CurrentUser Long userId,
        @RequestBody @Validated BookmarkSaveRequest request
    ) {
        bookmarkUseCase.save(userId, request);
        return CommonResponse.createSuccess(BOOKMARK_SAVE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{bookmarkId}")
    @Operation(summary = "북마크 삭제 API", description = "북마크를 삭제합니다.")
    public CommonResponse<Void> deleteBookmark(@CurrentUser Long userId, @PathVariable Long bookmarkId) {
        bookmarkUseCase.delete(userId, bookmarkId);
        return CommonResponse.createSuccess(BOOKMARK_DELETE_SUCCESS.getMessage());
    }

    @PatchMapping("/{bookmarkId}")
    @Operation(summary = "북마크 수정 API", description = "알림 정보와 함께 북마크를 수정합니다.")
    public CommonResponse<Void> updateBookmark(
        @CurrentUser Long userId,
        @PathVariable Long bookmarkId,
        @RequestBody @Validated BookmarkUpdateRequest request
    ) {
        bookmarkUseCase.update(userId, bookmarkId, request, notificationUseCase);

        return CommonResponse.createSuccess(BOOKMARK_UPDATE_SUCCESS.getMessage());
    }
}

package leets.bookmark.domain.bookmark.presentation;

import static leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage.*;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.application.usecase.BookmarkUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkUseCase bookmarkUseCase;
    private final BookmarkMapper bookmarkMapper;
    private final NotificationUseCase notificationUseCase;

    @GetMapping("/search")
    @Operation(summary = "북마크 메모 검색 API", description = "키워드를 포함하는 메모를 가진 북마크 목록을 조회합니다.")
    public CommonResponse<List<BookmarkResponse>> searchBookmarksByMemo(@CurrentUser Long userId, @RequestParam String keyword) {
        List<BookmarkResponse> result = bookmarkUseCase.getByMemoContaining(userId, keyword);
        return CommonResponse.createSuccess(BOOKMARK_MEMO_SEARCH_SUCCESS.getMessage(), result);
    }

    @GetMapping
    @Operation(summary = "북마크 필터링 API", description = "카테고리 ID는 필수이며, 태그 ID로 북마크를 추가 필터링합니다.")
    public CommonResponse<List<BookmarkResponse>> getFilteredBookmarks(
            @CurrentUser Long userId,
            @RequestParam List<Long> categoryIds,
            @RequestParam(required = false) List<Long> tagIds
    ) {
        BookmarkFilterRequest request = bookmarkMapper.toFilterRequest(categoryIds, tagIds);
        List<BookmarkResponse> result = bookmarkUseCase.getFilteredBookmarks(userId, request);
        return CommonResponse.createSuccess(BOOKMARK_FILTER_SUCCESS.getMessage(), result);
    }

    @GetMapping("/all")
    @Operation(summary = "전체 북마크 조회 API", description = "모든 북마크를 조회합니다.")
    public CommonResponse<List<BookmarkResponse>> getAllBookmarks(@CurrentUser Long userId) {
        List<BookmarkResponse> result = bookmarkUseCase.getAllBookmarks(userId);
        return CommonResponse.createSuccess(BOOKMARK_SEARCH_SUCCESS.getMessage(), result);
    }

    @GetMapping("/saved")
    @Operation(summary = "저장 북마크 리스트 조회 API", description = "모바일/PC 플랫폼 구분 후 최근순으로 n개씩 조회합니다.")
    public CommonResponse<Slice<BookmarkResponse>> getSavedBookmarksByPlatform(
            @CurrentUser Long userId,
            @RequestParam String platform,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Slice<BookmarkResponse> result = bookmarkUseCase.getSavedBookmarksByPlatform(userId, platform, PageRequest.of(page, size));
        return CommonResponse.createSuccess(BOOKMARK_FILTER_SUCCESS.getMessage(), result);
    }

    @GetMapping("/recent")
    @Operation(summary = "저장 북마크 무한스크롤 API", description = "모바일/PC 플랫폼에 따라 북마크를 최근순으로 slice하여 무한스크롤합니다.")
    public CommonResponse<Slice<BookmarkResponse>> getRecentBookmarksByPlatform(
            @CurrentUser Long userId,
            @RequestParam String platform,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Slice<BookmarkResponse> result = bookmarkUseCase.getRecentBookmarksByPlatform(userId, platform, PageRequest.of(page, size));
        return CommonResponse.createSuccess(BOOKMARK_FILTER_SUCCESS.getMessage(), result);
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "북마크 저장 API", description = "알림 정보와 함께 북마크를 저장할 수 있는 API입니다.")
    public CommonResponse<BookmarkResponse> saveBookmark(
            @CurrentUser Long userId,
            @RequestPart("request") @Validated BookmarkSaveRequest request
    ) {
        BookmarkResponse response = bookmarkUseCase.save(userId, request);
        return CommonResponse.createSuccess(BOOKMARK_SAVE_SUCCESS.getMessage(), response);
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
            @RequestPart("request") @Validated BookmarkUpdateRequest request
    ) {
        bookmarkUseCase.update(userId, bookmarkId, request);

        if (request.notification() != null) {
            BookmarkResponse bookmarkResponse = bookmarkUseCase.getById(userId, bookmarkId);
            notificationUseCase.saveNotification(
                bookmarkResponse.toBookmark().getUser(),
                bookmarkResponse.toBookmark(),
                request.notification()
            );
        }

        return CommonResponse.createSuccess(BOOKMARK_UPDATE_SUCCESS.getMessage());
    }
}

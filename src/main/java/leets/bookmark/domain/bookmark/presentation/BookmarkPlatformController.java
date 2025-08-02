package leets.bookmark.domain.bookmark.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPlatformResponse;
import leets.bookmark.domain.bookmark.application.usecase.BookmarkUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage.*;

@Tag(name = "PLATFORM", description = "북마크 플랫폼 관련 API")
@RestController
@RequestMapping("/api/v1/platforms")
@RequiredArgsConstructor
public class BookmarkPlatformController {

    private final BookmarkUseCase bookmarkUseCase;

    @GetMapping
    @Operation(summary = "플랫폼 조회 API", description = "사용자가 저장한 북마크의 플랫폼을 조회할 수 있는 API입니다.")
    public CommonResponse<List<BookmarkPlatformResponse>> getAllPlatforms(@CurrentUser Long userId) {
        List<BookmarkPlatformResponse> response = bookmarkUseCase.getAllPlatforms(userId);
        return CommonResponse.createSuccess(GET_ALL_BOOKMARK_PLATFORMS_SUCCESS.getMessage(), response);
    }
}

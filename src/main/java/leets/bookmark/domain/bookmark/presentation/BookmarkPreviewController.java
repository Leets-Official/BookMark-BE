package leets.bookmark.domain.bookmark.presentation;

import io.swagger.v3.oas.annotations.Operation;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.usecase.BookmarkUseCase;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/preview")
public class BookmarkPreviewController {

    private final BookmarkUseCase bookmarkUseCase;

    @GetMapping
    @Operation(
        summary = "북마크 미리보기 추출",
        description = "url을 통해 제목, 썸네일, 파비콘 정보를 추출합니다."
    )
    public CommonResponse<List<BookmarkPreviewResponse>> getPreview(@RequestParam("url") String url) {
        List<BookmarkPreviewResponse> result = bookmarkUseCase.extractPreviewFromUrl(url);
        return CommonResponse.createSuccess(BookmarkResponseMessage.BOOKMARK_PREVIEW_SUCCESS.getMessage(), result);
    }
}

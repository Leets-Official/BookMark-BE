package leets.bookmark.domain.bookmark.presentation;

import io.swagger.v3.oas.annotations.Operation;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.usecase.GetBookmarkPreviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkPreviewController {

    private final GetBookmarkPreviewUseCase getBookmarkPreviewUseCase;

    @Operation(
        summary = "북마크 미리보기 단건 조회 API",
        description = """
        특정 bookmarkId를 기반으로 해당 북마크의 썸네일과 제목을 반환합니다.
        이 API는 bookmarkId 하나에 대해서만 조회하며,
        응답은 BookmarkPreviewResponse 단일 객체로 반환됩니다.
    """
    )
    @GetMapping("/preview")
    public BookmarkPreviewResponse getBookmarkPreview(@RequestParam("id") Long bookmarkId) {
        return getBookmarkPreviewUseCase.getBookmarkPreview(bookmarkId);
    }
}

package leets.bookmark.domain.bookmark.presentation;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.usecase.GetBookmarkPreviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkPreviewController {

    private final GetBookmarkPreviewUseCase getBookmarkPreviewUseCase;

    @GetMapping("/preview")
    public BookmarkPreviewResponse getBookmarkPreview(@RequestParam("id") Long bookmarkId) {
        return getBookmarkPreviewUseCase.getBookmarkPreview(bookmarkId);
    }
}

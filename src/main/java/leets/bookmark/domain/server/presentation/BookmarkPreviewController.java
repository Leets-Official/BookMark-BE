package leets.bookmark.domain.server.presentation;

import leets.bookmark.domain.server.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.server.application.usecase.GetBookmarkPreviewUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkPreviewController {

    private final GetBookmarkPreviewUseCase getBookmarkPreviewUseCase;

    public BookmarkPreviewController(GetBookmarkPreviewUseCase getBookmarkPreviewUseCase) {
        this.getBookmarkPreviewUseCase = getBookmarkPreviewUseCase;
    }

    @GetMapping("/preview")
    public BookmarkPreviewResponse getBookmarkPreview(@RequestParam("id") Long bookmarkId) {
        return getBookmarkPreviewUseCase.execute(bookmarkId);
    }
}

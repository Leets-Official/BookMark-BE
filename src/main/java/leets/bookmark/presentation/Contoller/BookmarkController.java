package leets.bookmark.presentation.Contoller;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.application.usecase.GetBookmarkByMemoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final GetBookmarkByMemoUseCase getBookmarkByMemoUseCase;

    @GetMapping("/search")
    public ResponseEntity<List<BookmarkResponse>> searchBookmarksByMemo(@RequestParam String keyword) {
        List<BookmarkResponse> result = getBookmarkByMemoUseCase.execute(keyword);
        return ResponseEntity.ok(result);
    }
}

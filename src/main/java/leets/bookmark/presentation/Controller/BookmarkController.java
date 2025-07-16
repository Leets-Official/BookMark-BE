package leets.bookmark.presentation.Controller;

import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.global.common.response.ResponseMessage;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.application.usecase.GetByMemoContainingUseCase;
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

    private final GetByMemoContainingUseCase getBookmarkByMemoUseCase;

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<BookmarkResponse>>> searchBookmarksByMemo(@RequestParam String keyword) {
        ResponseEntity<CommonResponse<List<BookmarkResponse>>> response = getBookmarkByMemoUseCase.GetByMemoContaining(keyword);
        List<BookmarkResponse> result = response.getBody().getData();
        return ResponseEntity.ok(CommonResponse.createSuccess(ResponseMessage.BOOKMARK_SEARCH_SUCCESS.getMessage(), result));
    }
}

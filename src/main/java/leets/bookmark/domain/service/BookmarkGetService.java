package leets.bookmark.domain.service;
import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.global.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookmarkGetService {
    ResponseEntity<CommonResponse<List<BookmarkResponse>>> getBookmarksByMemo(String keyword);
}

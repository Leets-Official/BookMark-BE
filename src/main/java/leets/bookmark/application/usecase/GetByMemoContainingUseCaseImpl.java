package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetByMemoContainingUseCaseImpl implements GetByMemoContainingUseCase {

    private final BookmarkGetService bookmarkGetService;

    @Override
    public CommonResponse<List<BookmarkResponse>> getByMemoContaining(String keyword) {
        return bookmarkGetService.getBookmarksAllByMemo(keyword);
    }
}

package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.service.BookmarkGetService;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetByMemoContainingUseCaseImpl implements GetByMemoContainingUseCase {

    private final BookmarkGetService bookmarkGetService;

    @Override
    public CommonResponse<List<BookmarkResponse>> getByMemoContaining(String keyword) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksAllByMemo(keyword);
        List<BookmarkResponse> responses = bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
        return CommonResponse.createSuccess("북마크 메모 검색 성공", responses);
    }
}

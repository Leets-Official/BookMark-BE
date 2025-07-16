package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.service.BookmarkGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetByMemoContainingUseCaseImpl implements GetByMemoContainingUseCase {

    private final BookmarkGetService bookmarkGetService;

    @Override
    public List<BookmarkResponse> GetByMemoContaining(String keyword) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksAllByMemo(keyword);
        return bookmarks.stream()
                .map(BookmarkMapper::toResponse)
                .toList();
    }
}

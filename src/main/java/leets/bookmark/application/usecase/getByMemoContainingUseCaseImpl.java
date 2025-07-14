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
public class getByMemoContainingUseCaseImpl implements getByMemoContainingUseCase {

    private final BookmarkGetService bookmarkGetService;

    @Override
    public List<BookmarkResponse> getByMemoContaining(String keyword) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByMemo(keyword);
        return bookmarks.stream()
                .map(BookmarkMapper::toResponse)
                .toList();
    }
}

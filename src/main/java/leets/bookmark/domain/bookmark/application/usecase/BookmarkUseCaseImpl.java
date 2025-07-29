package leets.bookmark.domain.bookmark.application.usecase;

import java.util.List;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkPreviewService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkGetService bookmarkGetService;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkPreviewService bookmarkPreviewService;

    @Override
    public List<BookmarkResponse> getByMemoContaining(Long userId, String keyword) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByMemoContaining(keyword, userId);
        return mapToResponses(bookmarks);
    }

    @Override
    public List<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        List<Bookmark> bookmarks = bookmarkGetService.getFilteredBookmarks(userId, request);
        return mapToResponses(bookmarks);
    }

    @Override
    public List<BookmarkResponse> getAllBookmarks(Long userId) {
        List<Bookmark> bookmarks = bookmarkGetService.getAllBookmarks(userId);
        return mapToResponses(bookmarks);
    }

    private List<BookmarkResponse> mapToResponses(List<Bookmark> bookmarks) {
        return bookmarks.stream()
            .map(bookmark -> {
                List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
                return bookmarkMapper.toResponse(bookmark, mappings);
            })
            .toList();
    }

    @Override
    public List<BookmarkResponse> getFilteredBookmarksByCategory(Long userId, Long categoryId) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByCategoryIncludingUntagged(userId, categoryId);
        return bookmarks.stream()
                .map(bookmark -> {
                    List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
                    return bookmarkMapper.toResponse(bookmark, mappings);
                })
                .toList();
    }

    @Override
    public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
        return bookmarkPreviewService.extractPreviewFromUrl(url);
    }
}

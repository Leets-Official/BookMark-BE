package leets.bookmark.domain.bookmark.application.usecase;

import java.util.List;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.exception.NoBookmarkPermissionException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkDeleteService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkSaveService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkUpdateService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkGetService bookmarkGetService;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkDeleteService bookmarkDeleteService;
    private final BookmarkSaveService bookmarkSaveService;
    private final BookmarkUpdateService bookmarkUpdateService;

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
    public void delete(Long userId, Long bookmarkId) {
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);

        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }

        bookmarkDeleteService.delete(bookmark);
    }

    @Override
    public void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request) {
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);

        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }

        bookmarkUpdateService.update(bookmark, request);
    }

    @Override
    public void save(Long userId, BookmarkSaveRequest request) {
        Bookmark bookmark = bookmarkMapper.toEntity(userId, request);
        bookmarkSaveService.save(bookmark);
    }

}

package leets.bookmark.domain.bookmark.application.usecase;

import java.util.List;

import leets.bookmark.domain.bookmark.application.exception.BookmarkTagCountExceededException;
import leets.bookmark.domain.bookmark.application.exception.BookmarkTagMinimumRequiredException;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import org.springframework.web.multipart.MultipartFile;
import leets.bookmark.domain.file.domain.service.FileSaveService;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.exception.NoBookmarkPermissionException;
import leets.bookmark.domain.bookmark.application.exception.InvalidBookmarkCategoryException;
import leets.bookmark.domain.bookmark.application.exception.BookmarkTagCountExceededException;
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
    private final NotificationUseCase notificationUseCase;
    private final FileSaveService fileSaveService;
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
    public BookmarkResponse getById(Long userId, Long bookmarkId) {
        Bookmark bookmark = getAuthorizedBookmark(userId, bookmarkId);
        List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
        return bookmarkMapper.toResponse(bookmark, mappings);
    }

    @Override
    public List<BookmarkResponse> getFilteredBookmarksByCategory(Long userId, Long categoryId, String platform) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByCategoryIncludingUntagged(userId, categoryId, platform);
        return mapToResponses(bookmarks);
    }

    @Override
    public void delete(Long userId, Long bookmarkId) {
        Bookmark bookmark = getAuthorizedBookmark(userId, bookmarkId);
        bookmarkDeleteService.delete(bookmark);
    }

    @Override
    public void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request) {
        Bookmark bookmark = getAuthorizedBookmark(userId, bookmarkId);

        bookmarkUpdateService.update(bookmark, request);

        notificationUseCase.saveNotification(
            bookmark.getUser(),
            bookmark,
            request.notification()
        );
    }

    @Override
    public void save(Long userId, BookmarkSaveRequest request) {

        if (request.categoryId() == null) {
            throw new InvalidBookmarkCategoryException();
        }
        if (request.tagIds() == null || request.tagIds().isEmpty()) {
            throw new BookmarkTagMinimumRequiredException();
        }
        if (request.tagIds().size() > 3) {
            throw new BookmarkTagCountExceededException();
        }

        Bookmark bookmark = bookmarkMapper.toBookmark(userId, request);
        bookmarkSaveService.save(bookmark);

        notificationUseCase.saveNotification(bookmark.getUser(), bookmark, request.notification());
    }

    private Bookmark getAuthorizedBookmark(Long userId, Long bookmarkId) {
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }
        return bookmark;
    }

    @Override
    public Slice<BookmarkResponse> getSavedBookmarksByPlatform(Long userId, String platform, Pageable pageable) {
        return bookmarkGetService.getSavedBookmarksByPlatform(userId, platform, pageable)
            .map(this::toResponseWithMappings);
    }

    @Override
    public Slice<BookmarkResponse> getRecentBookmarksByPlatform(Long userId, String platform, Pageable pageable) {
        return bookmarkGetService.getRecentBookmarksByPlatform(userId, platform, pageable)
            .map(this::toResponseWithMappings);
    }


    private BookmarkResponse toResponseWithMappings(Bookmark bookmark) {
        List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
        return bookmarkMapper.toResponse(bookmark, mappings);
    }
}

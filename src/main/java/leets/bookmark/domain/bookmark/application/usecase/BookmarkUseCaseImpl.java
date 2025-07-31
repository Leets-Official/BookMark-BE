package leets.bookmark.domain.bookmark.application.usecase;

import java.util.List;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.bookmark.domain.service.BookmarkPreviewService;
import leets.bookmark.domain.file.application.dto.request.FileUpdateRequest;
import leets.bookmark.domain.file.application.usecase.FileUseCase;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.exception.NoBookmarkPermissionException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkDeleteService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkSaveService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkUpdateService;
import leets.bookmark.domain.user.domain.service.UserGetService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkGetService bookmarkGetService;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkDeleteService bookmarkDeleteService;
    private final BookmarkSaveService bookmarkSaveService;
    private final NotificationUseCase notificationUseCase;
    private final BookmarkUpdateService bookmarkUpdateService;
    private final UserGetService userGetService;
    private final BookmarkPreviewService bookmarkPreviewService;
    private final FileUseCase fileUseCase;

    @Override
    public List<BookmarkResponse> getByMemoContaining(Long userId, String keyword) {
        User user = userGetService.findById(userId);
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByMemoContaining(keyword, user);
        return mapToResponses(bookmarks);
    }

    @Override
    public List<BookmarkResponse> getAllBookmarks(Long userId) {
        User user = userGetService.findById(userId);
        List<Bookmark> bookmarks = bookmarkGetService.getAllBookmarks(user);
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
    public void save(Long userId, BookmarkSaveRequest request) {
        User user = userGetService.findById(userId);

        Bookmark bookmark = bookmarkSaveService.save(request, user);

        if (request.file() != null) {
            fileUseCase.saveFile(user, bookmark, request.file());
        }
    }

    @Override
    public void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request, NotificationUseCase notificationUseCase) {
        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        validateBookmarkOwner(userId, bookmark);

        bookmarkUpdateService.update(bookmark, request);

        if (request.file() != null) {
            FileUpdateRequest fileUpdateRequest = new FileUpdateRequest(
                request.file().fileName(),
                request.file().fileUrl()
            );
            fileUseCase.updateFile(user, bookmark, fileUpdateRequest);
        }

        if (request.notification() != null) {
            notificationUseCase.saveNotification(user, bookmark, request.notification());
        }
    }

    @Override
    public void delete(Long userId, Long bookmarkId) {
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        validateBookmarkOwner(userId, bookmark);

        fileUseCase.deleteFile(bookmark.getUser(), bookmark);
        bookmarkDeleteService.delete(bookmark);
    }

    private void validateBookmarkOwner(Long userId, Bookmark bookmark) {
        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }
    }

    private Bookmark getAuthorizedBookmark(Long userId, Long bookmarkId) {
        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }
        return bookmark;
    }

    @Override
    public Slice<BookmarkResponse> getSavedBookmarks(Long userId, Platform platform, Pageable pageable) {
        User user = userGetService.findById(userId);
        return bookmarkGetService.getSavedBookmarks(user, platform, pageable)
            .map(this::toResponseWithMappings);
    }

    @Override
    public Slice<BookmarkResponse> getRecentBookmarks(Long userId, Platform platform, Pageable pageable) {
        User user = userGetService.findById(userId);
        return bookmarkGetService.getRecentBookmarks(user, platform, pageable)
            .map(this::toResponseWithMappings);
    }

    private BookmarkResponse toResponseWithMappings(Bookmark bookmark) {
        List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
        return bookmarkMapper.toResponse(bookmark, mappings);
    }

    @Override
    public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
        return bookmarkPreviewService.extractPreviewFromUrl(url);
    }
}

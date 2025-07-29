package leets.bookmark.domain.bookmark.application.usecase;

import java.util.List;

import leets.bookmark.domain.bookmark.application.exception.BookmarkTagCountExceededException;
import leets.bookmark.domain.bookmark.application.exception.BookmarkTagMinimumRequiredException;
import leets.bookmark.domain.bookmark.domain.entity.enums.Provider;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.domain.file.domain.service.FileSaveService;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.exception.NoBookmarkPermissionException;
import leets.bookmark.domain.bookmark.application.exception.InvalidBookmarkCategoryException;
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
    private final UserGetService userGetService;

    @Override
    public List<BookmarkResponse> getByMemoContaining(Long userId, String keyword) {
        User user = userGetService.findById(userId);
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByMemoContaining(keyword, user);
        return mapToResponses(bookmarks);
    }

    @Override
    public List<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        User user = userGetService.findById(userId);
        List<Bookmark> bookmarks = bookmarkGetService.getFilteredBookmarks(user, request);
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
    public BookmarkResponse save(Long userId, BookmarkSaveRequest request) {

        if (request.categoryId() == null) {
            throw new InvalidBookmarkCategoryException();
        }
        if (request.tagIds() == null || request.tagIds().isEmpty()) {
            throw new BookmarkTagMinimumRequiredException();
        }
        if (request.tagIds().size() > 3) {
            throw new BookmarkTagCountExceededException();
        }

        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkSaveService.save(request, user);

        notificationUseCase.saveNotification(bookmark.getUser(), bookmark, request.notification());

        List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
        return bookmarkMapper.toResponse(bookmark, mappings);
    }

    private Bookmark getAuthorizedBookmark(Long userId, Long bookmarkId) {
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        if (!bookmark.getUser().getId().equals(userId)) {
            throw new NoBookmarkPermissionException();
        }
        return bookmark;
    }

    @Override
    public Slice<BookmarkResponse> getSavedBookmarksByPlatform(Long userId, DeviceType deviceType, Provider provider, Pageable pageable) {
        User user = userGetService.findById(userId);
        return bookmarkGetService.getSavedBookmarksByPlatform(user, deviceType, pageable)
            .map(this::toResponseWithMappings);
    }

    @Override
    public Slice<BookmarkResponse> getRecentBookmarksByPlatform(Long userId, DeviceType deviceType, Provider provider,Pageable pageable) {
        User user = userGetService.findById(userId);
        return bookmarkGetService.getRecentBookmarksByPlatform(user, deviceType, pageable)
            .map(this::toResponseWithMappings);
    }


    private BookmarkResponse toResponseWithMappings(Bookmark bookmark) {
        List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
        return bookmarkMapper.toResponse(bookmark, mappings);
    }
}

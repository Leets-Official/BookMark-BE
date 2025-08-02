package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import org.springframework.data.domain.Slice;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public interface BookmarkUseCase {
    Slice<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkSearchRequest request);

    void delete(Long userId, Long bookmarkId);

    void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request, NotificationUseCase notificationUseCase);

    void save(Long userId, BookmarkSaveRequest request);


    List<BookmarkPreviewResponse> extractPreviewFromUrl(String url);
}

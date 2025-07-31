package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
import leets.bookmark.domain.bookmark.domain.entity.enums.Provider;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Service
public interface BookmarkUseCase {
    List<BookmarkResponse> getByMemoContaining(Long userId, String keyword);

    List<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkFilterRequest request);

    List<BookmarkResponse> getAllBookmarks(Long userId);

    BookmarkResponse getById(Long userId, Long bookmarkId);

    void delete(Long userId, Long bookmarkId);

    void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request);

    BookmarkResponse save(Long userId, BookmarkSaveRequest request);

    Slice<BookmarkResponse> getSavedBookmarksByPlatform(Long userId, DeviceType deviceType, Provider provider, Pageable pageable);

    Slice<BookmarkResponse> getRecentBookmarksByPlatform(Long userId, DeviceType deviceType, Provider provider, Pageable pageable);

    List<BookmarkPreviewResponse> extractPreviewFromUrl(String url);
}

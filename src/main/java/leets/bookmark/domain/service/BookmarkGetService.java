package leets.bookmark.domain.service;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.global.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;

    public CommonResponse<List<BookmarkResponse>> getBookmarksAllByMemo(String keyword) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMemoContaining(keyword);
        List<BookmarkResponse> responseList = bookmarks.stream()
            .map(bookmark -> BookmarkResponse.builder()
                .id(bookmark.getId())
                .url(bookmark.getUrl())
                .title(bookmark.getTitle())
                .memo(bookmark.getMemo())
                .thumbnailUrl(bookmark.getThumbnailUrl())
                .createdAt(bookmark.getCreatedAt())
                .updatedAt(bookmark.getUpdatedAt())
                .build())
            .toList();

        return CommonResponse.createSuccess(
            ResponseMessage.BOOKMARK_SEARCH_SUCCESS.getMessage(), responseList
        );
    }
}

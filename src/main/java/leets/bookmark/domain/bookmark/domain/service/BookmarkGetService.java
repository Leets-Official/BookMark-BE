package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.application.usecase.BookmarkUseCase;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.global.common.response.CommonResponse;
import static leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService implements BookmarkUseCase {

    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarksAllByMemo(String keyword) {
        return bookmarkRepository.findByMemoContaining(keyword);
    }

    @Override
    public CommonResponse<List<BookmarkResponse>> getFilteredBookmarks(BookmarkFilterRequest request) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllWithFilter(request.categoryId(), request.tagNames());
        List<BookmarkResponse> response = bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
        return CommonResponse.createSuccess(BOOKMARK_FILTER_SUCCESS.getMessage(), response);
    }

    @Override
    public CommonResponse<List<BookmarkResponse>> getByMemoContaining(String keyword) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMemoContaining(keyword);
        List<BookmarkResponse> response = bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
        return CommonResponse.createSuccess(BOOKMARK_MEMO_SEARCH_SUCCESS.getMessage(), response);
    }

    @Override
    public CommonResponse<List<BookmarkResponse>> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        List<BookmarkResponse> response = bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
        return CommonResponse.createSuccess(BOOKMARK_SEARCH_SUCCESS.getMessage(), response);
    }
}

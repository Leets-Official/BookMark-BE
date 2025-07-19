package leets.bookmark.domain.bookmark.application.usecase;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.global.common.response.CommonResponse;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage.*;

@Service
@RequiredArgsConstructor
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<BookmarkResponse> getFilteredBookmarks(BookmarkFilterRequest request) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllWithFilter(request.categoryId(), request.tagNames());
        return bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
    }

    @Override
    public List<BookmarkResponse> getByMemoContaining(String keyword) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMemoContaining(keyword);
        return bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
    }

    @Override
    public List<BookmarkResponse> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        return bookmarks.stream()
            .map(BookmarkMapper::toResponse)
            .toList();
    }
}

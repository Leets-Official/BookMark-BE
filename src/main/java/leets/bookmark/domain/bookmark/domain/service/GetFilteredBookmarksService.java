package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.application.usecase.GetFilteredBookmarksUseCase;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFilteredBookmarksService implements GetFilteredBookmarksUseCase {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<BookmarkResponse> getFilteredBookmarks(BookmarkFilterRequest request) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllWithFilter(request.categoryId(), request.tagNames());
        return bookmarks.stream()
                .map(BookmarkMapper::toResponse)
                .toList();
    }
}

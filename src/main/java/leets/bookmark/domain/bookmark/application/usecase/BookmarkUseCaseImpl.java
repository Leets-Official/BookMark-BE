package leets.bookmark.domain.bookmark.application.usecase;

import java.util.List;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;
    private final TagRepository tagRepository;

    @Override
    public List<BookmarkResponse> getByMemoContaining(Long userId, String keyword) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMemoContainingAndUserId(keyword, userId);
        return bookmarks.stream()
            .map(bookmark -> {
                List<BookmarkTagMapping> mappings = bookmarkTagMappingRepository.findAllByBookmark(bookmark);
                return BookmarkMapper.toResponse(bookmark, mappings);
            })
            .toList();
    }

    @Override
    public List<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        List<Bookmark> bookmarks;
        if (request.tagId() == null) {
            bookmarks = bookmarkRepository.findAllByUserIdAndBookmarkTagMappings_Tag_Category_Id(userId, request.categoryId());
        } else {
            bookmarks = bookmarkRepository.findAllWithFilter(userId, request.categoryId(), request.tagId());
        }
        return bookmarks.stream()
            .map(bookmark -> {
                List<BookmarkTagMapping> mappings = bookmarkTagMappingRepository.findAllByBookmark(bookmark);
                return BookmarkMapper.toResponse(bookmark, mappings);
            })
            .toList();
    }

    @Override
    public List<BookmarkResponse> getAllBookmarks(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId);
        return bookmarks.stream()
            .map(bookmark -> {
                List<BookmarkTagMapping> mappings = bookmarkTagMappingRepository.findAllByBookmark(bookmark);
                return BookmarkMapper.toResponse(bookmark, mappings);
            })
            .toList();
    }

    @Override
    public List<Long> getTagIdsByBookmarkId(Long bookmarkId) {
        List<BookmarkTagMapping> mappings = bookmarkTagMappingRepository.findAllByBookmark_Id(bookmarkId);
        return mappings.stream()
            .map(mapping -> mapping.getTag().getId())
            .toList();
    }
}

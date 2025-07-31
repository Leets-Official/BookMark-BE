package leets.bookmark.domain.bookmark.application.usecase;

import java.util.ArrayList;
import java.util.List;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.dto.request.CategoryTagRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkSearchConditionMapper;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkPreviewService;
import leets.bookmark.domain.file.domain.service.FileGetService;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.service.TagGetService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkGetService bookmarkGetService;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkPreviewService bookmarkPreviewService;
    private final FileGetService fileGetService;

    private final CategoryGetService categoryGetService;
    private final TagGetService tagGetService;

    private final BookmarkSearchConditionMapper bookmarkSearchConditionMapper;

    @Override
    public List<BookmarkResponse> getByMemoContaining(Long userId, String keyword) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByMemoContaining(keyword, userId);
        return mapToResponses(bookmarks);
    }

    @Override
    public Slice<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkSearchRequest request) {
        Pageable pageable = PageRequest.of(request.page(),
                request.size(),
                Sort.by(Sort.Direction.DESC, "id"));

        List<Category> categories = new ArrayList<>();
        List<Tag> categoryWithTags = new ArrayList<>();

        List<CategoryTagRequest> categoryTagRequests = request.categoryTagRequests();
        if (categoryTagRequests != null) {
            for (CategoryTagRequest categoryTagRequest : categoryTagRequests) {
                Category category = categoryGetService.findById(categoryTagRequest.categoryId());

                if (categoryTagRequest.tagIds() == null || categoryTagRequest.tagIds().isEmpty()) {
                    categories.add(category);
                } else {
                    for (Long tagId : categoryTagRequest.tagIds()) {
                        Tag tag = tagGetService.findByIdAndCategoryId(tagId, category);
                        categoryWithTags.add(tag);
                    }
                }
            }
        }

        BookmarkSearchCondition condition = bookmarkSearchConditionMapper.toBookmarkSearchCondition(

                request, categories, categoryWithTags);

        Slice<Bookmark> bookmarks = bookmarkGetService.search(userId, condition, pageable);

        return bookmarks.map(bookmark -> {
            File file = fileGetService.findByBookmarkId(bookmark.getId());
            return bookmarkMapper.toResponse(bookmark, bookmarkGetService.getMappingsByBookmark(bookmark), file);
        });
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
                    File file = fileGetService.findByBookmarkId(bookmark.getId());
                    return bookmarkMapper.toResponse(bookmark, mappings, file);
                })
                .toList();
    }

    @Override
    public List<BookmarkResponse> getFilteredBookmarksByCategory(Long userId, Long categoryId) {
        List<Bookmark> bookmarks = bookmarkGetService.getBookmarksByCategoryIncludingUntagged(userId, categoryId);
        return bookmarks.stream()
                .map(bookmark -> {
                    List<BookmarkTagMapping> mappings = bookmarkGetService.getMappingsByBookmark(bookmark);
                    File file = fileGetService.findByBookmarkId(bookmark.getId());
                    return bookmarkMapper.toResponse(bookmark, mappings, file);
                })
                .toList();
    }

    @Override
    public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
        return bookmarkPreviewService.extractPreviewFromUrl(url);
    }
}

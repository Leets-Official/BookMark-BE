package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkFilterRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.application.dto.request.CategoryTagRequest;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.service.TagGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;

    private final TagGetService tagGetService;
    private final CategoryGetService categoryGetService;

    public List<Bookmark> getBookmarksByMemoContaining(String keyword, Long userId) {
        return bookmarkRepository.findByMemoContainingAndUserId(keyword, userId);
    }

    public List<BookmarkTagMapping> getMappingsByBookmark(Bookmark bookmark) {
        return bookmarkTagMappingRepository.findAllByBookmarkId(bookmark.getId());
    }

    public List<Bookmark> getBookmarksByCategoryIncludingUntagged(Long userId, Long categoryId) {
        return bookmarkRepository.findAllByUserIdAndCategoryId(userId, categoryId);
    }

    public List<Bookmark> getFilteredBookmarks(Long userId, Long categoryId, List<Long> tagIds) {
        return bookmarkRepository.findAllWithFilter(userId, categoryId, tagIds);
    }

    public List<Bookmark> getFilteredBookmarks(Long userId, BookmarkFilterRequest request) {
        Long categoryId = request.categoryId();
        List<Long> tagIds = request.tagId();

        if (tagIds == null || tagIds.isEmpty()) {
            return bookmarkRepository.findAllByUserIdAndCategoryId(userId, categoryId);
        }
        return bookmarkRepository.findAllWithFilter(userId, categoryId, tagIds);
    }

    public List<Bookmark> getAllBookmarks(Long userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }

    public Slice<Bookmark> search(Long userId, BookmarkSearchRequest request, Pageable pageable) {
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

        BookmarkSearchCondition condition = new BookmarkSearchCondition(
                request.platform(),
                categories,
                categoryWithTags
        );

        return bookmarkRepository.searchWithFilters(userId, condition, pageable);
    }
}

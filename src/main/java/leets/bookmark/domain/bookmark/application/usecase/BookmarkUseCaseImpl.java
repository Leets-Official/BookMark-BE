package leets.bookmark.domain.bookmark.application.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.dto.request.CategoryTagRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.exception.TagCategoryMismatchException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkSearchConditionMapper;
import leets.bookmark.domain.category.application.mapper.CategoryMapper;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.domain.service.BookmarkPreviewService;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.file.application.usecase.FileUseCase;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.service.FileGetService;
import leets.bookmark.domain.notification.domain.service.NotificationDeleteService;
import leets.bookmark.domain.notification.domain.service.NotificationGetService;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.exception.BookmarkPermissionDeniedException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.service.TagGetService;
import leets.bookmark.domain.user.domain.service.UserGetService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import leets.bookmark.domain.bookmark.domain.service.BookmarkDeleteService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkSaveService;
import leets.bookmark.domain.bookmark.domain.service.BookmarkUpdateService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkUseCaseImpl implements BookmarkUseCase {

    private final BookmarkGetService bookmarkGetService;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkDeleteService bookmarkDeleteService;
    private final BookmarkSaveService bookmarkSaveService;
    private final BookmarkUpdateService bookmarkUpdateService;
    private final UserGetService userGetService;
    private final BookmarkPreviewService bookmarkPreviewService;

    private final CategoryGetService categoryGetService;
    private final TagGetService tagGetService;

    private final BookmarkSearchConditionMapper bookmarkSearchConditionMapper;
    private final FileUseCase fileUseCase;
    private final NotificationUseCase notificationUseCase;
    private final NotificationDeleteService notificationDeleteService;
    private final NotificationGetService notificationGetService;

    private final FileMapper fileMapper;
    private final FileGetService fileGetService;

    @Override
    @Transactional(readOnly = true)
    public Slice<BookmarkResponse> getFilteredBookmarks(Long userId, BookmarkSearchRequest request) {
        User user = userGetService.findById(userId);

        Pageable pageable = PageRequest.of(request.page(),
                request.size(),
                Sort.by(Sort.Direction.DESC, "id"));

        List<CategoryTagRequest> categoryTagRequests = request.categoryTagRequests();

        List<Long> categoryIds = categoryTagRequests.stream()
                .map(CategoryTagRequest::categoryId)
                .toList();
        List<Category> categories = categoryGetService.findAllByIdInAndUser(categoryIds, user);

        Map<Long, Category> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));

        Map<Long, List<Long>> categoryToTagIds = categoryTagRequests.stream()
                .filter(r -> r.tagIds() != null && !r.tagIds().isEmpty())
                .collect(Collectors.toMap(
                        CategoryTagRequest::categoryId,
                        CategoryTagRequest::tagIds
                ));

        List<Category> categoriesWithoutTags = new ArrayList<>();
        List<Tag> filteredTags = new ArrayList<>();

        // 태그가 존재하는 카테고리-태그는 filteredTags에 추가
        for (Map.Entry<Long, List<Long>> categoryAndTags : categoryToTagIds.entrySet()) {
            Long categoryId = categoryAndTags.getKey();
            List<Long> tagIds = categoryAndTags.getValue();

            Category category = categoryMap.get(categoryId);
            if (category == null) continue;

            List<Tag> tags = tagGetService.findAllByIds(tagIds);
            validateTags(tags, category);
            filteredTags.addAll(tags);
        }

        // 태그 없는 카테고리는 categoriesWithoutTags에 추가
        for (CategoryTagRequest req : categoryTagRequests) {
            if (req.tagIds() == null || req.tagIds().isEmpty()) {
                Category category = categoryMap.get(req.categoryId());
                if (category != null) {
                    categoriesWithoutTags.add(category);
                }
            }
        }

        BookmarkSearchCondition condition = bookmarkSearchConditionMapper.toBookmarkSearchCondition(
                request, categoriesWithoutTags, filteredTags);

        Slice<Bookmark> bookmarks = bookmarkGetService.search(user.getId(), condition, pageable);

        return bookmarks.map(bookmark -> {
                    FileResponse fileResponse = fileMapper.toFileResponse(bookmark.getFile());
                    return bookmarkMapper.toResponse(bookmark, bookmarkGetService.getMappingsByBookmark(bookmark), fileResponse);
                }
        );
    }

    @Transactional
    @Override
    public void save(Long userId, BookmarkSaveRequest request) {
        User user = userGetService.findById(userId);

        Category category = categoryGetService.findById(request.categoryId());
        Bookmark bookmark = bookmarkSaveService.save(request, user, category);

        if (request.file() != null) {
            fileUseCase.saveFile(user, bookmark, request.file());
        }
    }

    @Transactional
    @Override
    public void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request, NotificationUseCase notificationUseCase) {
        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        validateBookmarkOwner(userId, bookmark);

        bookmarkUpdateService.update(bookmark, request);

        if (request.file() != null) {
            fileUseCase.updateFile(user, bookmark, request.file());
        }

        if (request.notification() != null) {
            notificationUseCase.saveNotification(user, bookmark, request.notification());
        }
    }

    @Transactional
    @Override
    public void delete(Long userId, Long bookmarkId) {
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        validateBookmarkOwner(userId, bookmark);

        fileUseCase.deleteFile(bookmark.getUser(), bookmark);
        notificationGetService.findByBookmarkId(bookmarkId)
            .ifPresent(notificationDeleteService::delete);
        bookmarkDeleteService.delete(bookmark);
    }

    private void validateBookmarkOwner(Long userId, Bookmark bookmark) {
        if (!bookmark.getUser().getId().equals(userId)) {
            throw new BookmarkPermissionDeniedException();
        }
    }

    @Override
    public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
        return bookmarkPreviewService.extractPreviewFromUrl(url);
    }

    private void validateTags(List<Tag> tags, Category category){
        tags.forEach(tag -> {
            if(!tag.getCategory().equals(category)){
                throw new TagCategoryMismatchException();
            }
        });
    }
}

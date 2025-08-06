package leets.bookmark.domain.bookmark.application.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.dto.request.CategoryTagRequest;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkFullResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPlatformResponse;
import leets.bookmark.domain.bookmark.application.exception.BookmarkUpdateFieldEmptyException;
import leets.bookmark.domain.bookmark.application.exception.TagCategoryMismatchException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkPlatformMapper;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkSearchConditionMapper;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.category.application.exception.CategoryOwnerMismatchException;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.bookmark.domain.service.BookmarkPreviewService;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.file.application.usecase.FileUseCase;
import leets.bookmark.domain.notification.application.dto.request.NotificationSaveRequest;
import leets.bookmark.domain.notification.application.mapper.NotificationMapper;
import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
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
    private final UserGetService userGetService;
    private final BookmarkPreviewService bookmarkPreviewService;

    private final CategoryGetService categoryGetService;
    private final TagGetService tagGetService;

    private final BookmarkSearchConditionMapper bookmarkSearchConditionMapper;
    private final FileUseCase fileUseCase;
    private final NotificationDeleteService notificationDeleteService;
    private final NotificationGetService notificationGetService;
    private final NotificationUseCase notificationUseCase;
    private final NotificationMapper notificationMapper;

    private final FileMapper fileMapper;

    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;
    private final BookmarkPlatformMapper bookmarkPlatformMapper;

    @Override
    @Transactional(readOnly = true)
    public BookmarkFullResponse findBookmark(Long userId, Long bookmarkId){
        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);

        validateBookmarkOwner(user.getId(), bookmark);

        FileResponse fileResponse = fileMapper.toFileResponse(bookmark.getFile());
        NotificationResponse notificationResponse = notificationUseCase.getNotification(user, bookmark);
        return bookmarkMapper.toFullResponse(bookmark, bookmarkGetService.getMappingsByBookmark(bookmark), fileResponse, notificationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<BookmarkFullResponse> getFilteredBookmarks(Long userId, BookmarkSearchRequest request) {
        User user = userGetService.findById(userId);

        Pageable pageable = PageRequest.of(request.page(),
                request.size(),
                Sort.by(Sort.Direction.DESC, "id"));

        List<Category> categoriesWithoutTags = new ArrayList<>();
        List<Tag> filteredTags = new ArrayList<>();

        if(request.categoryTagRequests()!=null && !request.categoryTagRequests().isEmpty()) {

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
        }

        BookmarkSearchCondition condition = bookmarkSearchConditionMapper.toBookmarkSearchCondition(
                request, categoriesWithoutTags, filteredTags);

        Slice<Bookmark> bookmarks = bookmarkGetService.search(user.getId(), condition, pageable);

        return bookmarks.map(bookmark -> {
                    FileResponse fileResponse = fileMapper.toFileResponse(bookmark.getFile());
                    NotificationResponse notificationResponse = notificationUseCase.getNotification(user, bookmark);
                    return bookmarkMapper.toFullResponse(bookmark, bookmarkGetService.getMappingsByBookmark(bookmark),
                            fileResponse, notificationResponse);
                }
        );
    }

    @Transactional
    @Override
    public void save(Long userId, BookmarkSaveRequest request) {
        User user = userGetService.findById(userId);

        Category category = categoryGetService.findById(request.categoryId());
        Bookmark bookmark = bookmarkSaveService.save(request, user, category);

        if (request.thumbnailUrl() != null) {
            fileUseCase.saveThumbnailFile(user, bookmark, request.thumbnailUrl());
        }
        if (request.notification() != null) {
            notificationUseCase.saveNotification(user, bookmark, request.notification());
        }
    }

    @Transactional
    @Override
    public void update(Long userId, Long bookmarkId, BookmarkUpdateRequest request, NotificationUseCase notificationUseCase) {
        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);
        validateBookmarkOwner(userId, bookmark);

        boolean updated = false;

        if (request.title() != null && !request.title().trim().isEmpty()) {
            bookmark.updateTitle(request.title());
            updated = true;

        }
        if (request.memo() != null && !request.memo().trim().isEmpty()) {
            bookmark.updateMemo(request.memo());
            updated = true;

        }

        if (request.thumbnailUrl() != null) {
            fileUseCase.updateThumbnailImage(user, bookmark, request.thumbnailUrl());
            updated = true;
        }

        if (request.platform() != null) {
            bookmark.updatePlatform(request.platform());
            updated = true;
        }

        if (request.faviconUrl() != null && !request.faviconUrl().trim().isEmpty()) {
            bookmark.updateFaviconUrl(request.faviconUrl());
            updated = true;
        }

        if (request.url() != null && !request.url().trim().isEmpty()) {
            bookmark.updateUrl(request.url());
            updated = true;
        }

        if (request.notification() != null) {
            if (request.notification().notificationId() != null) {
                notificationUseCase.updateNotification(user, bookmark, request.notification());
            } else {
                NotificationSaveRequest saveRequest = bookmarkMapper.toNotificationSaveRequest(request.notification());
                notificationUseCase.saveNotification(user, bookmark, saveRequest);
            }
            updated = true;
        }

        if (request.tagIds() != null && !request.tagIds().isEmpty() && request.categoryId() == null) {
            Category category = bookmark.getCategory();
            validateCategoryOwner(user.getId(), category);

            List<Tag> tags = tagGetService.findAllByTagIdsAndCategoryId(request.tagIds(), category.getId());
            if (tags != null && !tags.isEmpty()) {
                bookmarkTagMappingRepository.deleteByBookmarkId(bookmarkId);
                List<BookmarkTagMapping> mappings = tags.stream()
                        .map(tag -> BookmarkTagMapping.of(tag, bookmark))
                        .toList();
                bookmarkTagMappingRepository.saveAll(mappings);

                updated = true;
            }
        }

        if (request.tagIds() != null && !request.tagIds().isEmpty() && request.categoryId() != null) {
            Category newCategory = categoryGetService.findById(request.categoryId());
            validateCategoryOwner(user.getId(), newCategory);

            List<Tag> tags = tagGetService.findAllByTagIdsAndCategoryId(request.tagIds(), newCategory.getId());

            bookmarkTagMappingRepository.deleteByBookmarkId(bookmarkId);
            List<BookmarkTagMapping> mappings = tags.stream()
                    .map(tag -> BookmarkTagMapping.of(tag, bookmark))
                    .toList();
            bookmarkTagMappingRepository.saveAll(mappings);

            bookmark.updateCategory(newCategory);
            updated = true;
        }

        if (!updated) {
            throw new BookmarkUpdateFieldEmptyException();
        }
    }

    @Transactional
    @Override
    public void delete(Long userId, Long bookmarkId) {
        User user = userGetService.findById(userId);
        Bookmark bookmark = bookmarkGetService.getBookmarkById(bookmarkId);

        validateBookmarkOwner(user.getId(), bookmark);

        bookmarkTagMappingRepository.deleteByBookmarkId(bookmarkId);
        if (bookmark.getFile() != null) {
            bookmark.deleteFile();
            fileUseCase.deleteFile(user, bookmark);
        }

        notificationGetService.findByBookmarkId(bookmarkId)
            .ifPresent(notificationDeleteService::delete);
        bookmarkDeleteService.delete(bookmark);
    }

    private void validateBookmarkOwner(Long userId, Bookmark bookmark) {
        if (!bookmark.getUser().getId().equals(userId)) {
            throw new BookmarkPermissionDeniedException();
        }
    }

    @Transactional
    @Override
    public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
        return bookmarkPreviewService.extractPreviewFromUrl(url);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookmarkPlatformResponse> getAllPlatforms(Long userId) {
        User user = userGetService.findById(userId);
        List<Bookmark> bookmarks = bookmarkGetService.getDistinctPlatformsByUser(user);
        return bookmarkPlatformMapper.toBookmarkPlatformResponseList(bookmarks);
    }

    private void validateTags(List<Tag> tags, Category category){
        tags.forEach(tag -> {
            if(!tag.getCategory().equals(category)){
                throw new TagCategoryMismatchException();
            }
        });
    }

    private void validateCategoryOwner(Long userId, Category category){
        if(!category.getUser().getId().equals(userId)) {
            throw new CategoryOwnerMismatchException();
        }
    }
}

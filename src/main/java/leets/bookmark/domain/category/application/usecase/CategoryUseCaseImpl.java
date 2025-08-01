package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.category.application.dto.request.CategoryCreateRequest;
import leets.bookmark.domain.category.application.dto.request.CategoryNameUpdateRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.application.dto.response.CategoryWithTagResponse;
import leets.bookmark.domain.category.application.exception.CategoryLimitExceedException;
import leets.bookmark.domain.category.application.exception.CategoryOwnerMismatchException;
import leets.bookmark.domain.category.application.exception.DuplicatedCategoryNameException;
import leets.bookmark.domain.category.application.mapper.CategoryMapper;
import leets.bookmark.domain.category.domain.service.CategoryDeleteService;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.category.domain.service.CategorySaveService;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryUpdateService;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.service.FileGetService;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.service.TagDeleteService;
import leets.bookmark.domain.tag.domain.service.TagGetService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {

    public static final int CATEGORY_LIMIT = 15;

    private final UserGetService userGetService;
    private final CategoryGetService categoryGetService;
    private final CategorySaveService categorySaveService;
    private final CategoryDeleteService categoryDeleteService;
    private final CategoryUpdateService categoryUpdateService;
    private final TagGetService tagGetService;
    private final TagDeleteService tagDeleteService;

    private final CategoryMapper categoryMapper;
    private final BookmarkGetService bookmarkGetService;

    @Transactional
    @Override
    public void save(Long userId, CategoryCreateRequest request) {
        User user = userGetService.findById(userId);

        checkExceededCategoryLimit(user);
        checkDuplicateCategoryName(user, request.categoryName());

        Category category = categoryMapper.toCategory(user, request);
        categorySaveService.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponse> getAllCategories(Long userId) {
        User user = userGetService.findById(userId);

        List<Category> categories = categoryGetService.getAllByUser(user);

        Map<Long, List<String>> thumbnailMap = new HashMap<>();

        for (Category category : categories) {
            List<Bookmark> bookmarks = bookmarkGetService.getRecent3BookmarksByCategory(category);

            List<String> thumbnailUrls = bookmarks.stream()
                    .map(Bookmark::getFile)
                    .map(File::getFileUrl)
                    .toList();

            thumbnailMap.put(category.getId(), thumbnailUrls);
        }

        return categoryMapper.toCategoryResponseList(categories, thumbnailMap);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryWithTagResponse> getAllCategoriesWithTags(Long userId) {
        User user = userGetService.findById(userId);

        List<Category> categories = categoryGetService.getAllByUser(user);
        List<Tag> tags = tagGetService.findAllByUser(user);

        return categoryMapper.toCategoryWithTagResponseList(categories, tags);
    }

    @Transactional
    @Override
    public void update(Long userId, Long categoryId, CategoryNameUpdateRequest request) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(categoryId);

        validateCategoryOwner(category, user);
        checkDuplicateCategoryName(user, request.categoryName());

        categoryUpdateService.update(category, request);
    }

    @Transactional
    @Override
    public void delete(Long userId, Long categoryId) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(categoryId);

        validateCategoryOwner(category, user);

        // TODO: 해당 카테고리 내에 컨텐츠(북마크DB)가 존재하면 삭제 불가능하도록 로직 추가

        tagDeleteService.deleteAllByCategory(category);
        categoryDeleteService.delete(categoryId);
    }

    private void validateCategoryOwner(Category category, User user) {
        if (!category.getUser().equals(user)) {
            throw new CategoryOwnerMismatchException();
        }
    }

    private void checkDuplicateCategoryName(User user, String categoryName) {
        if (categoryGetService.existsByUserIdAndCategoryName(user.getId(), categoryName)) {
            throw new DuplicatedCategoryNameException();
        }
    }

    private void checkExceededCategoryLimit(User user) {
        if (categoryGetService.countByUser(user) >= CATEGORY_LIMIT) {
            throw new CategoryLimitExceedException();
        }
    }
}

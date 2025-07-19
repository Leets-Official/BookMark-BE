package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CategoryCreateRequest;
import leets.bookmark.domain.category.application.dto.request.CategoryNameUpdateRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.application.exception.CategoryOwnerMismatchException;
import leets.bookmark.domain.category.application.exception.DuplicatedCategoryNameException;
import leets.bookmark.domain.category.application.mapper.CategoryMapper;
import leets.bookmark.domain.category.domain.service.CategoryDeleteService;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.category.domain.service.CategorySaveService;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryUpdateService;
import leets.bookmark.domain.tag.domain.service.TagDeleteService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {

    private final CategorySaveService categorySaveService;
    private final CategoryMapper categoryMapper;
    private final UserGetService userGetService;
    private final CategoryGetService categoryGetService;
    private final CategoryDeleteService categoryDeleteService;
    private final CategoryUpdateService categoryUpdateService;
    private final TagDeleteService tagDeleteService;

    @Transactional
    @Override
    public void save(Long userId, CategoryCreateRequest request) {
        User user = userGetService.findById(userId);

        validateCategoryName(user, request.categoryName());

        Category category = categoryMapper.toCategory(user, request);
        categorySaveService.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponse> getAllCategories(Long userId) {
        User user = userGetService.findById(userId);

        List<Category> categories = categoryGetService.getAllByUser(user);
        return categoryMapper.toCategoryResponseList(categories);
    }

    @Transactional
    @Override
    public void update(Long userId, Long categoryId, CategoryNameUpdateRequest request) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(categoryId);

        validateCategoryOwner(category, user);
        validateCategoryName(user, request.categoryName());

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

    private void validateCategoryName(User user, String categoryName) {
        if (categoryGetService.existsByUserIdAndCategoryName(user.getId(), categoryName)) {
            throw new DuplicatedCategoryNameException();
        }
    }
}

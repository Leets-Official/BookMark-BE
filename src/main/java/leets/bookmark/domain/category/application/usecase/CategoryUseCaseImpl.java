package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.application.exception.CategoryOwnerMismatchException;
import leets.bookmark.domain.category.application.exception.DuplicatedCategoryNameException;
import leets.bookmark.domain.category.application.mapper.CategoryMapper;
import leets.bookmark.domain.category.domain.service.CategoryDeleteService;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.category.domain.service.CategorySaveService;
import leets.bookmark.domain.category.domain.entity.Category;
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

    @Transactional
    @Override
    public void save(Long userId, CreateCategoryRequest request) {
        User user = userGetService.findById(userId);

        validateCategoryName(user, request);

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
    public void delete(Long userId, Long categoryId) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(categoryId);

        validateCategoryOwner(category, user);

        categoryDeleteService.delete(categoryId);
    }

    private void validateCategoryOwner(Category category, User user) {
        if (!category.getUser().equals(user)) {
            throw new CategoryOwnerMismatchException();
        }
    }

    private void validateCategoryName(User user, CreateCategoryRequest request) {
        if (categoryGetService.existsByUserIdAndCategoryName(user.getId(), request.categoryName())) {
            throw new DuplicatedCategoryNameException();
        }
    }
}

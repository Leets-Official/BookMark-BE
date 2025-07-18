package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;
import leets.bookmark.domain.category.application.mapper.CategoryMapper;
import leets.bookmark.domain.category.domain.service.CategorySaveService;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {

    private final CategorySaveService categorySaveService;
    private final CategoryMapper categoryMapper;
    private final UserGetService userGetService;

    @Transactional
    @Override
    public void save(Long userId, CreateCategoryRequest request) {
        User user = userGetService.findById(userId);
        Category category = categoryMapper.toCategory(user, request);
        categorySaveService.save(category);
    }
}

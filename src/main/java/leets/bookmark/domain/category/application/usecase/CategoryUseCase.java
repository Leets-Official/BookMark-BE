package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;
import leets.bookmark.domain.category.application.mapper.CategoryMapper;
import leets.bookmark.domain.category.domain.service.CategorySaveService;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategorySaveService categorySaveService;
    private final CategoryMapper categoryMapper;

    public void save(User user, CreateCategoryRequest request) {
        Category category = categoryMapper.toCategory(user, request);
        categorySaveService.save(category);
    }
}

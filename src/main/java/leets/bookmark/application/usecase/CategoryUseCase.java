package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.mapper.CategoryMapper;
import leets.bookmark.domain.service.CategorySaveService;
import leets.bookmark.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategorySaveService categorySaveService;
    private final CategoryMapper categoryMapper;

    public void save(Long userId, CreateCategoryRequest request) {
        Category category = categoryMapper.toCategory(userId, request);
        categorySaveService.save(category);
    }
}

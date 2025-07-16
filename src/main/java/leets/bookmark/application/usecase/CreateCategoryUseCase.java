package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.mapper.CategoryMapper;
import leets.bookmark.domain.service.CategorySaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategorySaveService categorySaveService;
    private final CategoryMapper categoryMapper;

    public void save(Long userId, CreateCategoryRequest request) {
        categorySaveService.save(categoryMapper.toCategory(userId, request));
    }
}

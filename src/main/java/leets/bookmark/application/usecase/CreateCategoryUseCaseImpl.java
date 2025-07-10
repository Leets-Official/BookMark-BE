package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;
import leets.bookmark.application.mapper.CategoryMapper;
import leets.bookmark.domain.entity.Category;
import leets.bookmark.domain.repository.CategoryRepository;
import leets.bookmark.domain.service.CategorySaveService;
import leets.bookmark.global.exception.DuplicateCategoryNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategorySaveService categorySaveService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(Long userId, CreateCategoryRequest request) {
        //if (categoryRepository.existsByUserIdAndName(userId, request.name())) {
            //throw new DuplicateCategoryNameException("이미 존재하는 카테고리입니다.");
        //}

        Category category = categoryMapper.toEntity(userId, request);
        Category saved = categorySaveService.save(category);
        return categoryMapper.toResponse(saved);
    }
}

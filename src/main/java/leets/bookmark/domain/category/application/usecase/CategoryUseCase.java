package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CategoryCreateRequest;
import leets.bookmark.domain.category.application.dto.request.CategoryNameUpdateRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.application.dto.response.CategoryWithTagResponse;

import java.util.List;

public interface CategoryUseCase {

    void save(Long userId, CategoryCreateRequest request);

    List<CategoryResponse> getAllCategories(Long userId);

    List<CategoryWithTagResponse> getAllCategoriesWithTags(Long userId);

    void update(Long userId, Long categoryId, CategoryNameUpdateRequest request);

    void delete(Long userId, Long categoryId);
}

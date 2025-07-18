package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CategoryCreateRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryUseCase {

    void save(Long userId, CategoryCreateRequest request);

    List<CategoryResponse> getAllCategories(Long userId);

    void delete(Long userId, Long categoryId);
}

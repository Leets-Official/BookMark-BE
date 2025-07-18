package leets.bookmark.domain.category.application.usecase;

import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;

public interface CategoryUseCase {

    void save(Long userId, CreateCategoryRequest request);
}

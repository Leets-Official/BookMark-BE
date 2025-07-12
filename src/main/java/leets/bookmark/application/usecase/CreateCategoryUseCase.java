package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;

public interface CreateCategoryUseCase {
    void createCategory(Long userId, CreateCategoryRequest request);
}

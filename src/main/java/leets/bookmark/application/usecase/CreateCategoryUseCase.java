package leets.bookmark.application.usecase;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;

public interface CreateCategoryUseCase {
    CategoryResponse createCategory(Long userId, CreateCategoryRequest request);
}
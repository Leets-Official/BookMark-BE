package leets.bookmark.application.mapper;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;
import leets.bookmark.domain.entity.Category;

public interface CategoryMapper {
    Category toEntity(Long userId, CreateCategoryRequest request);
    CategoryResponse toResponse(Category category);
}

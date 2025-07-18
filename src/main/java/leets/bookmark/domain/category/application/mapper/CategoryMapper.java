package leets.bookmark.domain.category.application.mapper;

import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(User user, CreateCategoryRequest request) {
        return Category.builder()
                .user(user)
                .categoryName(request.categoryName())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}

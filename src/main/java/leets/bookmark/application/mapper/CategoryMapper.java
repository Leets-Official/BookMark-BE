package leets.bookmark.application.mapper;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;
import leets.bookmark.domain.entity.Category;
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

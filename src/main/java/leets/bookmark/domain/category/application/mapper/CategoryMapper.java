package leets.bookmark.domain.category.application.mapper;

import leets.bookmark.domain.category.application.dto.request.CategoryCreateRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public Category toCategory(User user, CategoryCreateRequest request) {
        return Category.builder()
                .user(user)
                .categoryName(request.categoryName())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public List<CategoryResponse> toCategoryResponseList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }
}

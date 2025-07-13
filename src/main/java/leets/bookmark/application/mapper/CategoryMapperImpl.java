package leets.bookmark.application.mapper;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;
import leets.bookmark.domain.entity.Category;
//import leets.bookmark.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(Long userId, CreateCategoryRequest request) {
        // TODO: User 엔티티 머지 후 아래 코드로 교체
        // User user = new User();
        // user.setId(userId);

        return Category.builder()
                .categoryName(request.name())
                .build();
    }

    @Override
    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getCategoryName())
                .build();
    }
}

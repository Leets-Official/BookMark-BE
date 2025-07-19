package leets.bookmark.domain.category.domain.service;

import leets.bookmark.domain.category.application.dto.request.CategoryNameUpdateRequest;
import leets.bookmark.domain.category.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryUpdateService {

    public void update(Category category, CategoryNameUpdateRequest request) {
        category.updateCategoryName(request.categoryName());
    }
}

package leets.bookmark.domain.category.domain.service;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.category.domain.repository.CategoryRepository;
import leets.bookmark.domain.category.application.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryGetService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public List<Category> getAllByUser(User user) {
        return categoryRepository.findAllByUser(user);
    }

    public boolean existsByUserIdAndCategoryName(Long userId, String categoryName) {
        return categoryRepository.existsByUserIdAndCategoryName(userId, categoryName);
    }
}

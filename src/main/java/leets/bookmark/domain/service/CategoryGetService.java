package leets.bookmark.domain.service;

import leets.bookmark.domain.entity.Category;
import leets.bookmark.domain.repository.CategoryRepository;
import leets.bookmark.global.exception.EntityNotFoundException;
import leets.bookmark.global.common.entity.CategoryErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryGetService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }

    public List<Category> getByUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }
}

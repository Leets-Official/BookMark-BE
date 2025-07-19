package leets.bookmark.domain.category.domain.service;

import leets.bookmark.domain.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDeleteService {

    private final CategoryRepository categoryRepository;

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}

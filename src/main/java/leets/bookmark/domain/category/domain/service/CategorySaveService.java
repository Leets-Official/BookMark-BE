package leets.bookmark.domain.category.domain.service;
import leets.bookmark.domain.category.domain.repository.CategoryRepository;
import leets.bookmark.domain.category.domain.entity.Category;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository categoryRepository;

    public void save(Category category) {
        categoryRepository.save(category);
    }
}

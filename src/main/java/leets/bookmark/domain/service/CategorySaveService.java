package leets.bookmark.domain.service;
import leets.bookmark.domain.repository.CategoryRepository;
import leets.bookmark.domain.entity.Category;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}

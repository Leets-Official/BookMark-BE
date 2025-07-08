package leets.bookmark.domain.service;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}

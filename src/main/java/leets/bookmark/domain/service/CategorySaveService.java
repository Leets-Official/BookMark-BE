package leets.bookmark.domain.service;
import leets.bookmark.domain.repository.CategoryRepository;
import leets.bookmark.domain.entity.Category;
import leets.bookmark.global.exception.DuplicateCategoryNameException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository categoryRepository;

    /*
    public Category save(Category category, Long userId) {
        if (categoryRepository.existsByUserIdAndName(userId, category.getName())) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }
        return categoryRepository.save(category);
    }
    */

    /*
    public Category save(Category category) {
        if (category.getUser() == null || category.getUser().getId() == null) {
            throw new IllegalArgumentException("User 정보가 누락되었습니다.");
        }
        Long userId = category.getUser().getId();
        if (categoryRepository.existsByUserIdAndName(userId, category.getName())) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }
        return categoryRepository.save(category);
    }
    */
    public Category save(Category category) {
        System.out.println("[임시 처리] User 병합 전 - category 저장 로직 단축 호출");
        return categoryRepository.save(category);
    }
}

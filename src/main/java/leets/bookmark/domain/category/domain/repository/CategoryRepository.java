package leets.bookmark.domain.category.domain.repository;

import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import leets.bookmark.domain.category.domain.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser(User user);

    boolean existsByUserIdAndCategoryName(Long userId, String categoryName);

    long countByUser(User user);

    Optional<Category> findByIdAndUserId(Long categoryId, Long userId);
}

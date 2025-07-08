package leets.bookmark.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import leets.bookmark.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByUserIdAndName(Long userId, String name);
}

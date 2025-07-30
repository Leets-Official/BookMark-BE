package leets.bookmark.domain.tag.domain.repository;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByCategoryAndTagName(Category category, String tagName);

    List<Tag> findAllByCategory(Category category);

    void deleteAllByCategory(Category category);

    List<Tag> findAllByCategory_User(User categoryUser);

    long countByCategory(Category category);

    Optional<Tag> findByIdAndCategory(Long id, Category category);
}

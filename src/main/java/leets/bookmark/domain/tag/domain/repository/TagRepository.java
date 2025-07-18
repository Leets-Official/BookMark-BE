package leets.bookmark.domain.tag.domain.repository;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByCategoryAndTagName(Category category, String tagName);

    List<Tag> findAllByCategory(Category category);
}

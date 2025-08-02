package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

    List<Bookmark> findTop3ByCategoryOrderByCreatedAtDesc(Category category);

    List<Bookmark> findAllByCategory(Category category);

    List<Bookmark> findAllByUserOrderByCreatedAtDesc(User user);
}

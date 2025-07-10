package leets.bookmark.domain.repository;

import leets.bookmark.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByMemoContaining(String keyword);
}

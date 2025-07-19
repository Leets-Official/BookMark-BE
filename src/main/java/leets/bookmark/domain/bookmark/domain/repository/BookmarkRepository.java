package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByMemoContaining(String keyword);

    @Query("""
    SELECT DISTINCT b FROM Bookmark b
    JOIN b.categories c
    JOIN leets.bookmark.domain.tag.domain.entity.Tag t
    ON t.categoryId = c.id
    WHERE (:categoryId IS NULL OR c.id = :categoryId)
    AND (:tagNames IS NULL OR t.tagName IN :tagNames)
    """)
    List<Bookmark> findAllWithFilter(@Param("categoryId") Long categoryId, @Param("tagNames") List<String> tagNames);
}

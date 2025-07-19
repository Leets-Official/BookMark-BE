package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByMemoContainingAndUserId(String keyword, Long userId);

    List<Bookmark> findAllByUserId(Long userId);

    @Query("""
    SELECT DISTINCT b FROM Bookmark b
    LEFT JOIN b.bookmarkCategories c
    LEFT JOIN c.tags t
    WHERE b.user.id = :userId
    AND (:categoryId IS NULL OR c.id = :categoryId)
    AND (:tagNames IS NULL OR t.tagName IN :tagNames)
    """)
    List<Bookmark> findAllWithFilter(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId,
        @Param("tagNames") List<String> tagNames
    );
}

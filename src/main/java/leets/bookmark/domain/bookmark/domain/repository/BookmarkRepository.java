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
        LEFT JOIN BookmarkTagMapping m ON m.bookmark = b
        LEFT JOIN Tag t ON t = m.tag
        WHERE b.user.id = :userId
        AND (
            t.category.id = :categoryId
            OR (m.id IS NULL AND b.categoryId = :categoryId)
        )
    """)
    List<Bookmark> findAllByUserIdAndCategoryId(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId
    );

    @Query("""
        SELECT DISTINCT b FROM Bookmark b
        LEFT JOIN BookmarkTagMapping m ON m.bookmark = b
        LEFT JOIN Tag t ON t = m.tag
        WHERE b.user.id = :userId
        AND (:categoryId IS NULL OR t.category.id = :categoryId)
        AND (:tagIds IS NULL OR t.id IN (:tagIds))
    """)
    List<Bookmark> findAllWithFilter(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId,
        @Param("tagIds") List<Long> tagIds
    );
}

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
    SELECT DISTINCT t.bookmark FROM Tag t
    WHERE t.category.id = :categoryId
    AND (:tagNames IS NULL OR t.tagName IN :tagNames)
    AND t.category.user.id = :userId
    """)
    List<Bookmark> findAllWithFilter(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId,
        @Param("tagNames") List<String> tagNames
    );
}

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
    SELECT DISTINCT b FROM BookmarkTagMapping m
    JOIN m.bookmark b
    JOIN m.tag t
    WHERE t.category.id = :categoryId
      AND (:tagIds IS NULL OR t.id IN :tagIds)
      AND b.user.id = :userId
""")
    List<Bookmark> findAllWithFilter(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId,
        @Param("tagIds") List<Long> tagIds
    );
}

package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Slice;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByMemoContainingAndUserId(String keyword, Long userId);

    List<Bookmark> findAllByUserId(Long userId);

    Slice<Bookmark> findTopByUserIdAndPlatformOrderByIdDesc(Long userId, DeviceType platform, Pageable pageable);

    Slice<Bookmark> findByUserIdAndPlatformAndIdLessThanOrderByIdDesc(Long userId, DeviceType platform, Long lastBookmarkId, Pageable pageable);

    Slice<Bookmark> findByUserIdAndPlatformAndIsSavedTrue(Long userId, DeviceType platform,Pageable pageable);

    @Query("""
        SELECT DISTINCT b FROM Bookmark b
        LEFT JOIN BookmarkTagMapping m ON m.bookmark = b
        LEFT JOIN Tag t ON t = m.tag
        WHERE b.user.id = :userId
        AND (:categoryIds IS NULL OR t.category.id IN (:categoryIds))
        AND (:tagIds IS NULL OR t.id IN (:tagIds))
        AND (:deviceType IS NULL OR b.deviceType = :deviceType)
    """)
    List<Bookmark> findAllWithFilter(
        @Param("userId") Long userId,
        @Param("categoryIds") List<Long> categoryIds,
        @Param("tagIds") List<Long> tagIds,
        @Param("deviceType") DeviceType deviceType
    );

    @Query("""
        SELECT DISTINCT b FROM Bookmark b
        LEFT JOIN BookmarkTagMapping m ON m.bookmark = b
        LEFT JOIN Tag t ON t = m.tag
        WHERE b.user.id = :userId
        AND t.category.id IN :categoryIds
        AND (:deviceType IS NULL OR b.deviceType = :deviceType)
    """)
    List<Bookmark> findAllByUserIdAndCategoryIds(
            @Param("userId") Long userId,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("deviceType") DeviceType deviceType
    );

    Page<Bookmark> findByUserIdAndPlatformOrderByCreatedAtDesc(Long userId, DeviceType platform, Pageable pageable);

}

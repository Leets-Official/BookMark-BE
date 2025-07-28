package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkTagMappingRepository extends JpaRepository<BookmarkTagMapping, Long> {

    List<BookmarkTagMapping> findAllByBookmarkId(Long bookmarkId);

    void deleteByBookmark(Bookmark bookmark);
}

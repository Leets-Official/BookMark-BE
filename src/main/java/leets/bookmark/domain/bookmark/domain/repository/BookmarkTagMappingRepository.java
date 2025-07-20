package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.tag.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkTagMappingRepository extends JpaRepository<BookmarkTagMapping, Long> {

    List<BookmarkTagMapping> findAllByBookmark(Bookmark bookmark);
    List<BookmarkTagMapping> findAllByTag(Tag tag);
    List<BookmarkTagMapping> findAllByBookmark_Id(Long bookmarkId);
}

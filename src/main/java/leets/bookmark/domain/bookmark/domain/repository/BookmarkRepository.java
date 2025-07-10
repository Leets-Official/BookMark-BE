package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository {
    Bookmark findById(Long id);
}

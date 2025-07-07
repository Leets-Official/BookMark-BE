package leets.bookmark.domain.server.domain.repository;

import leets.bookmark.domain.server.domain.entity.Bookmark;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository {
    Bookmark findById(Long id);
}

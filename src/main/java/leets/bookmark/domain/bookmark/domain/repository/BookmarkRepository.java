package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookmarkRepository {
    Optional<Bookmark> findById(Long id);
}

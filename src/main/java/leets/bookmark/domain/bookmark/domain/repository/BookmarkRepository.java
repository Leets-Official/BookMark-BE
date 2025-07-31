package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

}

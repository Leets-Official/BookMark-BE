package leets.bookmark.domain.file.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.file.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByBookmarkId(Long bookmarkId);

    void deleteByBookmarkIn(Collection<Bookmark> bookmarks);
}

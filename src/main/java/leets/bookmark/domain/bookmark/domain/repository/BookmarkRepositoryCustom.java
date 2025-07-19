package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import java.util.List;

public interface BookmarkRepositoryCustom {
    List<Bookmark> findAllWithFilter(Long categoryId, List<String> tagNames);
}

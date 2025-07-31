package leets.bookmark.domain.bookmark.domain.repository;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BookmarkRepositoryCustom {
    Slice<Bookmark> searchWithFilters(Long userId, BookmarkSearchCondition bookmarkSearchCondition, Pageable pageable);
}

package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getUrl(),
                bookmark.getTitle(),
                bookmark.getMemo(),
                bookmark.getThumbnailUrl(),
                bookmark.getCategory() != null ? bookmark.getCategory().getCategoryName() : null,
                bookmark.getCreatedAt(),
                bookmark.getUpdatedAt()
        );
    }
}

package leets.bookmark.application.mapper;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.entity.Bookmark;

public class BookmarkMapper {

    public static BookmarkResponse toResponse(Bookmark bookmark) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getUrl(),
                bookmark.getTitle(),
                bookmark.getMemo(),
                bookmark.getThumbnailUrl(),
                null, // or bookmark.getCategory().getName() if available
                bookmark.getCreatedAt(),
                bookmark.getUpdatedAt()
        );
    }
}

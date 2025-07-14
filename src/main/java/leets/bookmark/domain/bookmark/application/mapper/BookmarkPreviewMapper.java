package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.stereotype.Component;

@Component
public class BookmarkPreviewMapper {

    public BookmarkPreviewResponse toResponse(Bookmark bookmark) {
        return BookmarkPreviewResponse.builder()
            .title(bookmark.getTitle())
            .thumbnailUrl(bookmark.getThumbnailUrl())
            .build();
    }
}

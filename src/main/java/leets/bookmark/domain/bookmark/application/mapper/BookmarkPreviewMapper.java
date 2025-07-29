package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import org.springframework.stereotype.Component;

@Component
public class BookmarkPreviewMapper {

    public BookmarkPreviewResponse toResponse(BookmarkPreviewResponse response) {
        return BookmarkPreviewResponse.builder()
                .title(response.title())
                .thumbnailUrl(response.thumbnailUrl())
                .faviconUrl(response.faviconUrl())
                .build();
    }

}

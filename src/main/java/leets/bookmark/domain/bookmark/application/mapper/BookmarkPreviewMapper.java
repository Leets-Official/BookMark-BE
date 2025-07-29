package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import org.springframework.stereotype.Component;

@Component
public class BookmarkPreviewMapper {

    public BookmarkResponse toResponse(BookmarkPreviewResponse response) {
        return BookmarkResponse.builder()
                .title(response.title())
                .thumbnailUrl(response.thumbnailUrl())
                .faviconUrl(response.faviconUrl())
                .build();
    }

}

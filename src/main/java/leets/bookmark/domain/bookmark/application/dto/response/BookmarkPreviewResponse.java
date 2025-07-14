package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkPreviewResponse {
    private String title;
    private String thumbnailUrl;
}

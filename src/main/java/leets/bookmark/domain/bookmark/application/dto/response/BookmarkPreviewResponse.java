package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkPreviewResponse {
    private String title;
    private String thumbnailUrl;
}

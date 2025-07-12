package leets.bookmark.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@Builder
public class BookmarkResponse {
    private Long id;
    private String url;
    private String title;
    private String memo;
    private String thumbnailUrl;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

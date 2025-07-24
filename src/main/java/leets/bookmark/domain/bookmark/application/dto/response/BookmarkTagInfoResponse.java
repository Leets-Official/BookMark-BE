package leets.bookmark.domain.bookmark.application.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record BookmarkTagInfoResponse(
    Long categoryId,
    String categoryName,
    List<TagInfo> tags
) {
    @Builder
    public record TagInfo(
            Long tagId,
            String tagName
    ) {}
}

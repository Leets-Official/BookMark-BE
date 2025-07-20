package leets.bookmark.domain.bookmark.application.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record BookmarkTagInfoResponse(
    Long categoryId,
    String categoryName,
    List<Long> tagId,
    List<String> tagNames
) {}

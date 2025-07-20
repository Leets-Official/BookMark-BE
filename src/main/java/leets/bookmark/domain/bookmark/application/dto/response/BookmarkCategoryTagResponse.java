package leets.bookmark.domain.bookmark.application.dto.response;

import java.util.List;
import lombok.Builder;
@Builder
public record BookmarkCategoryTagResponse(
        String categoryName,
        List<String> tagNames
) {}

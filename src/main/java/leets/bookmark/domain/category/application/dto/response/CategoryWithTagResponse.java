package leets.bookmark.domain.category.application.dto.response;

import leets.bookmark.domain.tag.application.dto.response.TagResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryWithTagResponse(
        Long categoryId,
        String categoryName,
        List<TagResponse> tags
) {
}

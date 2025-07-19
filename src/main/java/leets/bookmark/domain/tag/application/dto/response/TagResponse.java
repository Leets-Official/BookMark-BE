package leets.bookmark.domain.tag.application.dto.response;

import lombok.Builder;

@Builder
public record TagResponse(
        Long tagId,
        Long categoryId,
        String tagName
) {
}

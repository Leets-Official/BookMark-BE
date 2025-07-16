package leets.bookmark.application.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String categoryName) {
}

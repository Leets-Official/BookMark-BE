package leets.bookmark.domain.category.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record CategoryResponse(
        Long id,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

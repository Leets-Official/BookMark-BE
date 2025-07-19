package leets.bookmark.domain.category.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryNameUpdateRequest(
        @NotBlank String categoryName
) {
}

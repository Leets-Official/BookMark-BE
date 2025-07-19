package leets.bookmark.domain.category.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank String categoryName
) {
}

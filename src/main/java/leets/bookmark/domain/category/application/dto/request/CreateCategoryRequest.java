package leets.bookmark.domain.category.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank String categoryName
) {
}

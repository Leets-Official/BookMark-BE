package leets.bookmark.domain.tag.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TagCreateRequest (
        @NotNull Long categoryId,
        @NotBlank String tagName
) {
}

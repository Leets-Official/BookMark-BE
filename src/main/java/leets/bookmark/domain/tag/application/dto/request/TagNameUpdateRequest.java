package leets.bookmark.domain.tag.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TagNameUpdateRequest(
        @NotBlank String tagName
) {
}

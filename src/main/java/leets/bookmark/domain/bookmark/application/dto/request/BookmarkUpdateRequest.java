package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BookmarkUpdateRequest(
        @NotBlank String title,
        @NotBlank String memo,
        String url,
        String thumbnailUrl
) {
}
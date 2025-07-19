package leets.bookmark.domain.file.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FileUpdateRequest(
        @NotBlank
        String fileName,
        @NotBlank
        String fileUrl
) {}

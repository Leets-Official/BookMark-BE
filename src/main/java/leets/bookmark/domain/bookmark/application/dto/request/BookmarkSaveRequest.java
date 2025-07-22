package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BookmarkSaveRequest(
    @NotBlank String title,
    @NotBlank String fileUrl,
    String memo
) {}

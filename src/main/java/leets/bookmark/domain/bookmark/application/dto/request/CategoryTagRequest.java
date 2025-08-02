package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.annotation.Nullable;

import java.util.List;

public record CategoryTagRequest(
        Long categoryId,
        @Nullable List<Long> tagIds
) {}

package leets.bookmark.domain.bookmark.application.dto.request;

import lombok.Builder;

import java.util.List;
@Builder
public record BookmarkFilterRequest(
        Long categoryId,
        List<String> tagNames
) {}

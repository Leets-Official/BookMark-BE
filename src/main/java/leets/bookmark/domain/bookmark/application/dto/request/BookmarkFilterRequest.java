package leets.bookmark.domain.bookmark.application.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record BookmarkFilterRequest(
        List<Long> categoryIds,
        List<Long> tagId,
        String platform
) {}

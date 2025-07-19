package leets.bookmark.domain.bookmark.application.dto.request;

import java.util.List;

public record BookmarkFilterRequest(
        Long categoryId,
        List<String> tagNames
) {}

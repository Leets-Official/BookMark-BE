package leets.bookmark.domain.bookmark.application.dto.response;

import java.util.List;

public record BookmarkCategoryTagResponse(
        String categoryName,
        List<String> tagNames
) {}

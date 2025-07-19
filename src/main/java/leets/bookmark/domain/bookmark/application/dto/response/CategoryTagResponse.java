package leets.bookmark.domain.bookmark.application.dto.response;

import java.util.List;

public record CategoryTagResponse(
        String categoryName,
        List<String> tagNames
) {}
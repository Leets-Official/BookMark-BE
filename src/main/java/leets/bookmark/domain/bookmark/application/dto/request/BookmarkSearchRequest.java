package leets.bookmark.domain.bookmark.application.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;

import java.util.List;

public record BookmarkSearchRequest(
        String keyword,
        List<CategoryTagRequest> categoryTagRequests,
        List<Platform> platforms,
        @PositiveOrZero
        int page,
        @Positive
        int size
) {}

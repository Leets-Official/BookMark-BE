package leets.bookmark.domain.bookmark.application.dto.request;

import leets.bookmark.domain.bookmark.domain.entity.enums.DeviceType;
import leets.bookmark.domain.bookmark.domain.entity.enums.Provider;
import lombok.Builder;

import java.util.List;

@Builder
public record BookmarkFilterRequest(
    List<Long> categoryIds,
    List<Long> tagId,
    DeviceType deviceType,
    Provider provider
) {}

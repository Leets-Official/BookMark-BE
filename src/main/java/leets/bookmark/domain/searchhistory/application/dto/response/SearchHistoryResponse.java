package leets.bookmark.domain.searchhistory.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchHistoryResponse(
    Long id,
    String keyword,
    LocalDateTime searchedAt
) {}

package leets.bookmark.domain.server.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchHistoryResponse {
    private Long id;
    private String keyword;
    private LocalDateTime searchedAt;
}

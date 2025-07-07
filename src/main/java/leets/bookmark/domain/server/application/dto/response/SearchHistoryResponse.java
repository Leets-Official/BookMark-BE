package leets.bookmark.domain.server.application.dto.response;

import java.time.LocalDateTime;

public class SearchHistoryResponse {
    private Long id;
    private String keyword;
    private LocalDateTime searchedAt;

    public SearchHistoryResponse(Long id, String keyword, LocalDateTime searchedAt) {
        this.id = id;
        this.keyword = keyword;
        this.searchedAt = searchedAt;
    }

    public Long getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public LocalDateTime getSearchedAt() {
        return searchedAt;
    }
}

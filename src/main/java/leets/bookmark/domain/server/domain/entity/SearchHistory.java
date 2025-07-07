package leets.bookmark.domain.server.domain.entity;

import java.time.LocalDateTime;

public class SearchHistory {
    private Long id;
    private Long userId;
    private String keyword;
    private LocalDateTime searchedAt;

    public SearchHistory(Long id, Long userId, String keyword, LocalDateTime searchedAt) {
        this.id = id;
        this.userId = userId;
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

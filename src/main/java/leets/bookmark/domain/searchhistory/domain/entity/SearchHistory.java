package leets.bookmark.domain.searchhistory.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchHistory {
    private Long id;
    private Long userId;
    private String keyword;
    private LocalDateTime searchedAt;
}

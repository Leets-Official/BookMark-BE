package leets.bookmark.domain.searchhistory.application.mapper;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SearchHistoryMapper {

    public SearchHistoryResponse toResponse(SearchHistory history) {
        return SearchHistoryResponse.builder()
            .id(history.getId())
            .keyword(history.getKeyword())
            .searchedAt(history.getCreatedAt())
            .build();
    }

    public SearchHistory toEntity(User user, String keyword) {
        return SearchHistory.builder()
            .user(user)
            .keyword(keyword)
            .build();
    }
}

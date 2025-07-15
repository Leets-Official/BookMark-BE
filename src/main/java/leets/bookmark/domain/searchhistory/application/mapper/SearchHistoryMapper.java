package leets.bookmark.domain.searchhistory.application.mapper;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import org.springframework.stereotype.Component;

@Component
public class SearchHistoryMapper {

    public SearchHistoryResponse toResponse(SearchHistory history) {
        return new SearchHistoryResponse(
            history.getId(),
            history.getKeyword(),
            history.getCreatedAt()
        );
    }
}

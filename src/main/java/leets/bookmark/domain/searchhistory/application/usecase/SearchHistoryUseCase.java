package leets.bookmark.domain.searchhistory.application.usecase;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.mapper.SearchHistoryMapper;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryGetService;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryUseCase {

    private final SearchHistoryGetService searchHistoryGetService;
    private final SearchHistoryDeleteService searchHistoryDeleteService;
    private final SearchHistoryMapper searchHistoryMapper;

    public List<SearchHistoryResponse> getSearchHistory(Long userId) {
        return searchHistoryGetService.getSearchHistoriesByUserId(userId)
            .stream().map(searchHistoryMapper::toResponse).toList();
    }

    public void deleteSearchHistory(Long userId) {
        searchHistoryDeleteService.deleteByUserId(userId);
    }
}

package leets.bookmark.domain.searchhistory.application.usecase;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.mapper.SearchHistoryMapper;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryGetService;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryDeleteService;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryUseCase {

    private final SearchHistoryGetService searchHistoryGetService;
    private final SearchHistoryDeleteService searchHistoryDeleteService;
    private final SearchHistoryMapper searchHistoryMapper;

    public List<SearchHistoryResponse> getSearchHistory(User user) {
        return searchHistoryGetService.getSearchHistoriesByUser(user)
            .stream().map(searchHistoryMapper::toResponse).toList();
    }

    public void deleteSearchHistory(User user) {
        searchHistoryDeleteService.deleteByUser(user);
    }
}

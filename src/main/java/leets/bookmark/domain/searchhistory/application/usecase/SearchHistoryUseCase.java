package leets.bookmark.domain.searchhistory.application.usecase;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.mapper.SearchHistoryMapper;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryGetService;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryDeleteService;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryCreateService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryUseCase {

    private final SearchHistoryGetService searchHistoryGetService;
    private final SearchHistoryDeleteService searchHistoryDeleteService;
    private final SearchHistoryCreateService searchHistoryCreateService;
    private final SearchHistoryMapper searchHistoryMapper;
    private final UserGetService userGetService;

    public List<SearchHistoryResponse> getSearchHistory(Long userId) {
        User user = userGetService.findById(userId);
        return searchHistoryGetService.getSearchHistoriesByUser(user)
            .stream()
            .map(searchHistoryMapper::toResponse)
            .toList();
    }

    public void deleteSearchHistory(Long userId) {
        User user = userGetService.findById(userId);
        List<SearchHistory> histories = searchHistoryGetService.getSearchHistoriesByUser(user);
        histories.forEach(searchHistoryDeleteService::delete);
    }

    public void createSearchHistory(Long userId, String keyword) {
        searchHistoryCreateService.create(userId, keyword);
    }
}

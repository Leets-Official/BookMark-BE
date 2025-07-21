package leets.bookmark.domain.searchhistory.application.usecase;

import leets.bookmark.domain.searchhistory.application.dto.request.SearchHistoryRequest;
import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.mapper.SearchHistoryMapper;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryGetService;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistoryDeleteService;
import leets.bookmark.domain.searchhistory.domain.service.SearchHistorySaveService;
import leets.bookmark.domain.searchhistory.application.exception.SearchHistoryNotFoundException;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryUseCase {

    private final SearchHistoryGetService searchHistoryGetService;
    private final SearchHistoryDeleteService searchHistoryDeleteService;
    private final SearchHistorySaveService searchHistorySaveService;
    private final SearchHistoryMapper searchHistoryMapper;
    private final UserGetService userGetService;

    public List<SearchHistoryResponse> getSearchHistory(Long userId) {
        User user = userGetService.findById(userId);
        List<SearchHistory> histories = searchHistoryGetService.getSearchHistoriesByUser(user);
        return histories.stream()
            .map(searchHistoryMapper::toResponse)
            .toList();
    }

    public void deleteSearchHistory(Long userId, Long searchHistoryId) {
        User user = userGetService.findById(userId);
        SearchHistory history = searchHistoryGetService.findById(searchHistoryId);
        if (!history.getUser().equals(user)) {
            throw new SearchHistoryNotFoundException();
        }
        searchHistoryDeleteService.delete(history);
    }

    @Transactional
    public void saveSearchHistory(Long userId, SearchHistoryRequest request) {
        User user = userGetService.findById(userId);
        searchHistorySaveService.save(user, request.keyword());
    }
}

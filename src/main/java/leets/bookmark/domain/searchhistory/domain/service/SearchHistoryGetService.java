package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.searchhistory.application.exception.SearchHistoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryGetService {

    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> getSearchHistoriesByUser(User user) {
        return searchHistoryRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public SearchHistory findByIdAndUser(Long id, Long userId) {
        return searchHistoryRepository.findByIdAndUserId(id, userId)
            .orElseThrow(SearchHistoryNotFoundException::new);
    }
}

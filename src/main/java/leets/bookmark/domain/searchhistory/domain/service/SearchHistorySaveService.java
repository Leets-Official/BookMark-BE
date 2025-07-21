package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchHistorySaveService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional
    public void save(User user, String keyword) {
        searchHistoryRepository.findByUserAndKeyword(user, keyword)
            .ifPresent(searchHistoryRepository::delete);

        SearchHistory searchHistory = SearchHistory.builder()
            .user(user)
            .keyword(keyword)
            .build();

        searchHistoryRepository.save(searchHistory);
    }
}

package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchHistoryCreateService {

    private final SearchHistoryRepository searchHistoryRepository;

    public void create(User user, String keyword) {
        SearchHistory history = SearchHistory.builder()
            .user(user)
            .keyword(keyword)
            .build();

        searchHistoryRepository.save(history);
    }
}

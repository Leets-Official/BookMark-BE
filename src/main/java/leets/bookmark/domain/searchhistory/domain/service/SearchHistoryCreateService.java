package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchHistoryCreateService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserGetService userGetService;

    public void create(Long userId, String keyword) {
        User user = userGetService.findById(userId);
        SearchHistory history = SearchHistory.builder()
            .user(user)
            .keyword(keyword)
            .build();

        searchHistoryRepository.save(history);
    }
}

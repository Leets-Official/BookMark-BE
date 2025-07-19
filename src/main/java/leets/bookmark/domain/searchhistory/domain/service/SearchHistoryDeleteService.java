package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchHistoryDeleteService {

    private final SearchHistoryRepository searchHistoryRepository;

    public void delete(SearchHistory history) {
        searchHistoryRepository.delete(history);
    }
}

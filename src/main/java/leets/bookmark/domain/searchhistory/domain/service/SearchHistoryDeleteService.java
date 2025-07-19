package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchHistoryDeleteService {

    private final SearchHistoryRepository searchHistoryRepository;

    public void deleteByUserId(Long userId) {
        searchHistoryRepository.deleteByUserId(userId);
    }
}
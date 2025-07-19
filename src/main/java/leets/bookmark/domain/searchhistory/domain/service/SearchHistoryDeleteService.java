package leets.bookmark.domain.searchhistory.domain.service;

import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchHistoryDeleteService {

    private final SearchHistoryRepository searchHistoryRepository;

    public void deleteByUser(User user) {
        searchHistoryRepository.deleteByUser(user);
    }
}
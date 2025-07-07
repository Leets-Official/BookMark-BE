package leets.bookmark.domain.server.application.usecase;

import leets.bookmark.domain.server.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.server.domain.entity.SearchHistory;
import leets.bookmark.domain.server.domain.repository.SearchHistoryRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetSearchHistoryUseCase {

    private final SearchHistoryRepository searchHistoryRepository;

    public GetSearchHistoryUseCase(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    public List<SearchHistoryResponse> execute(Long userId) {
        List<SearchHistory> historyList = searchHistoryRepository.findByUserId(userId);

        return historyList.stream()
                .map(h -> new SearchHistoryResponse(
                        h.getId(),
                        h.getKeyword(),
                        h.getSearchedAt()
                ))
                .collect(Collectors.toList());
    }
}

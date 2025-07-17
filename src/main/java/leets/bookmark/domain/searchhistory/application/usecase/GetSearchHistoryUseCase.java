package leets.bookmark.domain.searchhistory.application.usecase;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.mapper.SearchHistoryMapper;
import leets.bookmark.domain.searchhistory.domain.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetSearchHistoryUseCase {

    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchHistoryMapper searchHistoryMapper;

    public List<SearchHistoryResponse> getSearchHistory(Long userId) {
        return List.copyOf(
            searchHistoryRepository.findByUserId(userId).stream()
                .map(searchHistoryMapper::toResponse)
                .toList()
        );
    }
}

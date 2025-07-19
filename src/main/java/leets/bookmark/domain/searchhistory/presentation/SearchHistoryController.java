package leets.bookmark.domain.searchhistory.presentation;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.usecase.SearchHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/search-histories")
public class SearchHistoryController {

    private final SearchHistoryUseCase searchHistoryUseCase;

    @GetMapping
    public List<SearchHistoryResponse> getSearchHistories(@RequestParam("userId") Long userId) {
        return searchHistoryUseCase.getSearchHistory(userId);
    }
    @DeleteMapping
    public void deleteSearchHistories(@RequestParam("userId") Long userId) {
        searchHistoryUseCase.deleteSearchHistory(userId);
    }
}

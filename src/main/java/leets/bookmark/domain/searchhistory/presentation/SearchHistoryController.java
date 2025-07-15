package leets.bookmark.domain.searchhistory.presentation;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.usecase.GetSearchHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search-histories")
public class SearchHistoryController {

    private final GetSearchHistoryUseCase getSearchHistoryUseCase;

    @GetMapping
    public List<SearchHistoryResponse> getSearchHistories(@RequestParam("userId") Long userId) {
        return getSearchHistoryUseCase.getSearchHistory(userId);
    }
}

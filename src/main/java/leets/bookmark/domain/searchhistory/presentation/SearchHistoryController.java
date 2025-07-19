package leets.bookmark.domain.searchhistory.presentation;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.usecase.SearchHistoryUseCase;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/search-histories")
public class SearchHistoryController {

    private final SearchHistoryUseCase searchHistoryUseCase;

    @GetMapping
    public List<SearchHistoryResponse> getSearchHistories(@CurrentUser User user) {
        return searchHistoryUseCase.getSearchHistory(user);
    }

    @DeleteMapping
    public void deleteSearchHistories(@CurrentUser User user) {
        searchHistoryUseCase.deleteSearchHistory(user);
    }
}

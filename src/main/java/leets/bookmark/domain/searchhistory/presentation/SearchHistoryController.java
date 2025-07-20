package leets.bookmark.domain.searchhistory.presentation;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.dto.request.SearchHistoryRequest;
import leets.bookmark.domain.searchhistory.application.usecase.SearchHistoryUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/search-histories")
public class SearchHistoryController {

    private final SearchHistoryUseCase searchHistoryUseCase;

    @GetMapping
    public CommonResponse<List<SearchHistoryResponse>> getSearchHistories(@CurrentUser Long userId) {
        List<SearchHistoryResponse> histories = searchHistoryUseCase.getSearchHistory(userId);
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.GET_SEARCH_HISTORY_SUCCESS.getMessage(), histories);
    }

    @PostMapping
    public CommonResponse<Void> saveSearchHistory(@CurrentUser Long userId, @RequestBody SearchHistoryRequest request) {
        searchHistoryUseCase.saveSearchHistory(userId, request);
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.SAVE_SEARCH_HISTORY_SUCCESS.getMessage());
    }

    @DeleteMapping("/{searchHistoryId}")
    public CommonResponse<Void> deleteSearchHistory(@CurrentUser Long userId, @PathVariable Long searchHistoryId) {
        searchHistoryUseCase.deleteSearchHistory(userId, searchHistoryId);
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.DELETE_SEARCH_HISTORY_SUCCESS.getMessage(), null);
    }
}

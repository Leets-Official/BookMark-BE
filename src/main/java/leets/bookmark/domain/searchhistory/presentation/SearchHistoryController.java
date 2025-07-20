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
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.GET_SUCCESS.getMessage(), histories);
    }

    @PostMapping
    public CommonResponse<Void> createSearchHistory(@CurrentUser Long userId, @RequestBody SearchHistoryRequest request) {
        searchHistoryUseCase.createSearchHistory(userId, request.keyword());
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.CREATE_SUCCESS.getMessage(), null);
    }

    @DeleteMapping
    public CommonResponse<Void> deleteSearchHistories(@CurrentUser Long userId) {
        searchHistoryUseCase.deleteSearchHistory(userId);
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.DELETE_SUCCESS.getMessage(), null);
    }
}

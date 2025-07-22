package leets.bookmark.domain.searchhistory.presentation;

import leets.bookmark.domain.searchhistory.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.searchhistory.application.usecase.SearchHistoryUseCase;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.domain.searchhistory.presentation.SearchHistoryResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/search-histories")
public class SearchHistoryController {

    private final SearchHistoryUseCase searchHistoryUseCase;

    @GetMapping
    public CommonResponse<List<SearchHistoryResponse>> getSearchHistories(@CurrentUser User user) {
        List<SearchHistoryResponse> histories = searchHistoryUseCase.getSearchHistory(user);
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.GET_SUCCESS.getMessage(), histories);
    }

    @DeleteMapping
    public CommonResponse<Void> deleteSearchHistories(@CurrentUser User user) {
        searchHistoryUseCase.deleteSearchHistory(user);
        return CommonResponse.createSuccess(SearchHistoryResponseMessage.DELETE_SUCCESS.getMessage(), null);
    }
}

package leets.bookmark.domain.server.presentation;

import leets.bookmark.domain.server.application.dto.response.SearchHistoryResponse;
import leets.bookmark.domain.server.application.usecase.GetSearchHistoryUseCase;
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
        return getSearchHistoryUseCase.execute(userId);
    }
}

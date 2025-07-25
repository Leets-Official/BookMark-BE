package leets.bookmark.domain.searchhistory.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class SearchHistoryNotFoundException extends BusinessException {
    public SearchHistoryNotFoundException() {
        super(SearchHistoryErrorCode.SEARCH_HISTORY_NOT_FOUND_EXCEPTION);
    }
}

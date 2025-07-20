package leets.bookmark.domain.searchhistory.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class DuplicatedSearchHistoryNameException extends BusinessException {
    public DuplicatedSearchHistoryNameException() {
        super(SearchHistoryErrorCode.DUPLICATED_SEARCH_HISTORY_KEYWORD_EXCEPTION);
    }
}
package leets.bookmark.domain.searchhistory.application.exeception;

import leets.bookmark.global.common.exception.BusinessException;

public class SearchHistoryNotFoundException extends BusinessException {
    public SearchHistoryNotFoundException() {
        super(SearchHistoryErrorCode.NOT_FOUND);
    }
}

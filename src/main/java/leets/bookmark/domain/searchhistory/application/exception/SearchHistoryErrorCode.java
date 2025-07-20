package leets.bookmark.domain.searchhistory.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchHistoryErrorCode implements ErrorCode {

    SEARCH_HISTORY_NOT_FOUND_EXCEPTION(404,"해당하는 파일을 찾을 수 없습니다.");

    private final int errorCode;
    private final String message;

}

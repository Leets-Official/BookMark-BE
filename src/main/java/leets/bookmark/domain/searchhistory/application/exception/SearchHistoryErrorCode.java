package leets.bookmark.domain.searchhistory.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchHistoryErrorCode implements ErrorCode {

    SEARCH_HISTORY_NOT_FOUND_EXCEPTION(404,"해당하는 검색 기록을 찾을 수 없습니다."),
    DUPLICATED_SEARCH_HISTORY_KEYWORD_EXCEPTION(400,"이미 동일한 검색 기록이 존재합니다.");

    private final int errorCode;
    private final String message;
}

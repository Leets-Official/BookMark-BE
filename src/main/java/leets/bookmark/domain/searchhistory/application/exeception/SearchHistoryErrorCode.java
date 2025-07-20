package leets.bookmark.domain.searchhistory.application.exeception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SearchHistoryErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "검색 기록을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public int getErrorCode() {
        return status.value();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

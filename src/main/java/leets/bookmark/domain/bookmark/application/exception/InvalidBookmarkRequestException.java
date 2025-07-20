package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidBookmarkRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidBookmarkRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

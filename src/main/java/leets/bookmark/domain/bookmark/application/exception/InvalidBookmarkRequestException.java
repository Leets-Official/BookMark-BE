package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;
import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidBookmarkRequestException extends BusinessException {
    public InvalidBookmarkRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class InvalidPlatformException extends BusinessException {
    public InvalidPlatformException(){
        super(BookmarkErrorCode.INVALID_PLATFORM_EXCEPTION);
    }
}

package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class InvalidProviderException extends BusinessException {
    public InvalidProviderException() {
        super(BookmarkErrorCode.INVALID_PROVIDER_EXCEPTION);
    }
}

package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class InvalidDeviceTypeException extends BusinessException {
    public InvalidDeviceTypeException() {
        super(BookmarkErrorCode.INVALID_DEVICE_TYPE_EXCEPTION);
    }
}

package leets.bookmark.domain.user.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND_EXCEPTION);
    }
}

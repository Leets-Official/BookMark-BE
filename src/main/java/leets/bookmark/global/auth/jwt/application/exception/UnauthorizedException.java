package leets.bookmark.global.auth.jwt.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(AuthJwtErrorCode.UNAUTHORIZED_EXCEPTION);
    }
}

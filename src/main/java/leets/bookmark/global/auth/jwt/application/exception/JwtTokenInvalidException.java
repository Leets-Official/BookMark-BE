package leets.bookmark.global.auth.jwt.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class JwtTokenInvalidException extends BusinessException {
    public JwtTokenInvalidException() {
        super(AuthJwtErrorCode.JWT_TOKEN_INVALID_EXCEPTION);
    }
}

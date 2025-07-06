package leets.bookmark.global.auth.jwt.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(403, "인증되지 않은 사용자입니다.");
    }
}

package leets.bookmark.global.auth.oauth2.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class KakaoAccessTokenInvalidException extends BusinessException {
    public KakaoAccessTokenInvalidException() {
        super(OAuth2ErrorCode.KAKAO_ACCESS_TOKEN_INVALID_EXCEPTION);
    }
}

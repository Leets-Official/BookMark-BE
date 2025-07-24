package leets.bookmark.global.auth.oauth2.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class KakaoAuthorizationCodeInvalidException extends BusinessException {
    public KakaoAuthorizationCodeInvalidException() {
        super(OAuth2ErrorCode.KAKAO_AUTHORIZATION_CODE_INVALID_EXCEPTION);
    }
}

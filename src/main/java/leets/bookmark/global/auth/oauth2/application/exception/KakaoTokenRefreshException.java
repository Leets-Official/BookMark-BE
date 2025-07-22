package leets.bookmark.global.auth.oauth2.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class KakaoTokenRefreshException extends BusinessException {
    public KakaoTokenRefreshException(){
        super(OAuth2ErrorCode.KAKAO_TOKEN_REFRESH_EXCEPTION);
    }
}

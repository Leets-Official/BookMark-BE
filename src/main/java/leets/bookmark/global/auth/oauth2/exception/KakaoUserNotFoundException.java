package leets.bookmark.global.auth.oauth2.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class KakaoUserNotFoundException extends BusinessException {
    public KakaoUserNotFoundException() {
        super(OAuth2ErrorCode.KAKAO_USER_NOT_FOUND_EXCEPTION);
    }
}

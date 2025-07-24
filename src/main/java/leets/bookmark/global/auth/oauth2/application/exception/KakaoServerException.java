package leets.bookmark.global.auth.oauth2.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class KakaoServerException extends BusinessException {
    public KakaoServerException() {
        super(OAuth2ErrorCode.KAKAO_SERVER_EXCEPTION);
    }
}

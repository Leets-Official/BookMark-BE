package leets.bookmark.global.auth.oauth2.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class OAuth2UserInfoException extends BusinessException {
    public OAuth2UserInfoException() {
        super(OAuth2ErrorCode.OAUTH2_USER_INFO_EXCEPTION);
    }
}

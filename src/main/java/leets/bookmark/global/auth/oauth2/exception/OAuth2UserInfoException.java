package leets.bookmark.global.auth.oauth2.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class OAuth2UserInfoException extends BusinessException {
    public OAuth2UserInfoException() {
        super(400, "카카오 계정에 email 정보가 없습니다.");
    }
}

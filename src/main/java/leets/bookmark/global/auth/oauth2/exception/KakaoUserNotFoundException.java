package leets.bookmark.global.auth.oauth2.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class KakaoUserNotFoundException extends BusinessException {
    public KakaoUserNotFoundException() {
        super(404, "해당 Kakao 사용자를 찾을 수 없습니다.");
    }
}

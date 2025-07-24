package leets.bookmark.global.auth.oauth2.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2ErrorCode implements ErrorCode {

    // OAuth2 전체 관련
    OAUTH2_USER_INFO_EXCEPTION(400, "email 정보가 없습니다."),

    // Kakao 관련
    KAKAO_USER_NOT_FOUND_EXCEPTION(400, "해당 Kakao 사용자를 찾을 수 없습니다."),
    KAKAO_TOKEN_REFRESH_EXCEPTION(400, "카카오 토큰 갱신을 실패하였습니다"),
    KAKAO_AUTHORIZATION_CODE_INVALID_EXCEPTION(400, "카카오 인가 코드가 유효하지 않습니다."),
    KAKAO_ACCESS_TOKEN_INVALID_EXCEPTION(400, "카카오 엑세스 토큰이 유효하지 않습니다."),
    KAKAO_SERVER_EXCEPTION(500, "카카오 서버 오류가 발생했습니다.");

    private final int errorCode;
    private final String message;
}

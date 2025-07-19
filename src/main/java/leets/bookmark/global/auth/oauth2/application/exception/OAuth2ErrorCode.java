package leets.bookmark.global.auth.oauth2.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum OAuth2ErrorCode implements ErrorCode {

    // OAuth2 전체 관련
    OAUTH2_USER_INFO_EXCEPTION(BAD_REQUEST.value(), "email 정보가 없습니다."),

    // Kakao 관련
    KAKAO_USER_NOT_FOUND_EXCEPTION(NOT_FOUND.value(), "해당 Kakao 사용자를 찾을 수 없습니다.");

    private final int errorCode;
    private final String message;

}

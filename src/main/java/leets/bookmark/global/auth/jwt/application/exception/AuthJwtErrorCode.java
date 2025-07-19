package leets.bookmark.global.auth.jwt.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthJwtErrorCode implements ErrorCode {

    UNAUTHORIZED_EXCEPTION(UNAUTHORIZED.value(), "인증되지 않은 사용자입니다.");

    private final int errorCode;
    private final String message;

}

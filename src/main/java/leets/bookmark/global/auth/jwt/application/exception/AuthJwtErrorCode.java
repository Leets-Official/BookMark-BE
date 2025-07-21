package leets.bookmark.global.auth.jwt.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthJwtErrorCode implements ErrorCode {

    UNAUTHORIZED_EXCEPTION(UNAUTHORIZED.value(), "인증되지 않은 사용자입니다."),
    JWT_TOKEN_INVALID_EXCEPTION(401, "유효하지 않은 토큰입니다."),
    AES_ENCRYPT_EXCEPTION(500, "AES 암호화에 실패하였습니다."),
    AES_DECRYPT_EXCEPTION(500, "AES 복호화에 실패하였습니다.");

    private final int errorCode;
    private final String message;

}

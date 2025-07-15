package leets.bookmark.domain.user.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND_EXCEPTION(NOT_FOUND.value(),"해당 사용자가 존재하지 않습니다.");

    private final int errorCode;
    private final String message;
}

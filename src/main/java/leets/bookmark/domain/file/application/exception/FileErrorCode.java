package leets.bookmark.domain.file.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {

    FILE_NOT_FOUND_EXCEPTION(404,"해당하는 파일을 찾을 수 없습니다."),
    INVALID_FILE_EXTENSION(400, "올바르지 않은 파일 확장자입니다.");

    private final int errorCode;
    private final String message;

}

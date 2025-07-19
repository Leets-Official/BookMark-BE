package leets.bookmark.global.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int statusCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
    }
}

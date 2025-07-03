package leets.bookmark.global.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int statusCode;

    public BusinessException(int code, String message) {
        super(message);
        this.statusCode = code;
    }
}

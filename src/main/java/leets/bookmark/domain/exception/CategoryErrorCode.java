package leets.bookmark.domain.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum CategoryErrorCode implements ErrorCode {
    DUPLICATE_NAME(404, "이미 존재하는 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND(404, "카테고리를 찾을 수 없습니다.");

    private final int code;
    private final String message;

    private CategoryErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

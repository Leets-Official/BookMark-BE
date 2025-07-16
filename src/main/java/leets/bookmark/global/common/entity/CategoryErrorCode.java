package leets.bookmark.global.common.entity;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum CategoryErrorCode implements ErrorCode {
    DUPLICATE_NAME("404", "이미 존재하는 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND("404", "카테고리를 찾을 수 없습니다.");

    private final String code;
    private final String message;

    private CategoryErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

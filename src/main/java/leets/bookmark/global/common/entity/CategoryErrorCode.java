package leets.bookmark.global.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    DUPLICATE_NAME("이미 존재하는 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다.");

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}

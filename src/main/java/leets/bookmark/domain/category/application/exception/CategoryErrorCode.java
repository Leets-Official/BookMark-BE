package leets.bookmark.domain.category.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

    DUPLICATE_NAME(400, "이미 존재하는 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND(404, "카테고리를 찾을 수 없습니다."),
    CATEGORY_OWNER_MISMATCH_EXCEPTION(403, "해당 카테고리 소유자만 접근 가능합니다.");

    private final int errorCode;
    private final String message;
}

package leets.bookmark.domain.category.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

    DUPLICATE_CATEGORY_NAME_EXCEPTION(400, "이미 존재하는 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND_EXCEPTION(404, "카테고리를 찾을 수 없습니다."),
    CATEGORY_OWNER_MISMATCH_EXCEPTION(403, "해당 카테고리 소유자만 접근 가능합니다."),
    CATEGORY_LIMIT_EXCEED_EXCEPTION(400, "카테고리는 최대 15개까지 생성 가능합니다."),
    CATEGORY_HAS_BOOKMARK_EXCEPTION(404, "해당 카테고리에 북마크가 존재합니다.");

    private final int errorCode;
    private final String message;
}

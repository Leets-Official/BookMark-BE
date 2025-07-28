package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagErrorCode implements ErrorCode {

    TAG_NOT_FOUND_EXCEPTION(404,"해당 태그가 존재하지 않습니다."),
    DUPLICATED_TAG_NAME_EXCEPTION(400, "이미 존재하는 태그입니다."),
    TAG_OWNER_MISMATCH_EXCEPTION(403, "해당 태그 소유자만 접근 가능합니다."),
    TAG_LIMIT_EXCEED_EXCEPTION(400, "태그는 카테고리당 최대 10개까지 생성 가능합니다.");

    private final int errorCode;
    private final String message;
}

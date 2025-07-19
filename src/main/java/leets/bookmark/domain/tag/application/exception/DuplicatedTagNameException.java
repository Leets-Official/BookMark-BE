package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class DuplicatedTagNameException extends BusinessException {
    public DuplicatedTagNameException() {
        super(TagErrorCode.DUPLICATED_TAG_NAME_EXCEPTION);
    }
}

package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagOwnerMismatchException extends BusinessException {
    public TagOwnerMismatchException() {
        super(TagErrorCode.TAG_OWNER_MISMATCH_EXCEPTION);
    }
}

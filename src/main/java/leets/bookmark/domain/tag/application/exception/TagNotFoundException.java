package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagNotFoundException extends BusinessException {
    public TagNotFoundException() {
        super(TagErrorCode.TAG_NOT_FOUND_EXCEPTION);
    }
}

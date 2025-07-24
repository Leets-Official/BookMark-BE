package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagLimitExceedException extends BusinessException {
    public TagLimitExceedException() {
        super(TagErrorCode.TAG_LIMIT_EXCEED_EXCEPTION);
    }
}

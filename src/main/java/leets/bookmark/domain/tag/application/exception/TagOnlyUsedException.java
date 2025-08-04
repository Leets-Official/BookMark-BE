package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagOnlyUsedException extends BusinessException {
    public TagOnlyUsedException() {
        super(TagErrorCode.TAG_ONLY_USED_BOOKMARK_EXISTS);
    }
}

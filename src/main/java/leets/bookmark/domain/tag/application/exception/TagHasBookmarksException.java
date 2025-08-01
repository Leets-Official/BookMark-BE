package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagHasBookmarksException extends BusinessException {
    public TagHasBookmarksException() {
        super(TagErrorCode.TAG_HAS_BOOKMARK_EXCEPTION);
    }
}

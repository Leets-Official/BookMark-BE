package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class InvalidBookmarkCategoryException extends BusinessException {
    public InvalidBookmarkCategoryException() {
        super(BookmarkErrorCode.INVALID_BOOKMARK_CATEGORY_EXCEPTION);
    }
}

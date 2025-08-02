package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkTagMinimumRequiredException extends BusinessException {
    public BookmarkTagMinimumRequiredException() {
        super(BookmarkErrorCode.BOOKMARK_TAG_MINIMUM_REQUIRED_EXCEPTION);
    }
}

package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkNotFoundException extends BusinessException {
    public BookmarkNotFoundException() {
        super(BookmarkErrorCode.BOOKMARK_NOT_FOUND_EXCEPTION);
    }
}

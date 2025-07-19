package leets.bookmark.domain.bookmark.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkNotFoundException extends BusinessException {
    public BookmarkNotFoundException() {
        super(BookmarkErrorCode.BOOKMARK_NOT_FOUND_EXCEPTION);
    }
}

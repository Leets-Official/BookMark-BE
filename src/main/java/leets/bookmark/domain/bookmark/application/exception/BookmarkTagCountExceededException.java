package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkTagCountExceededException extends BusinessException {
    public BookmarkTagCountExceededException() {
        super(BookmarkErrorCode.BOOKMARK_TAG_COUNT_EXCEEDED_EXCEPTION);
    }
}

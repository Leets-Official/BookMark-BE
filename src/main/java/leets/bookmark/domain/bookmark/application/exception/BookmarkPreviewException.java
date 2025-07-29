package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkPreviewException extends BusinessException {
    public BookmarkPreviewException() {
        super(BookmarkErrorCode.BOOKMARK_PREVIEW_FAILED);
    }
}

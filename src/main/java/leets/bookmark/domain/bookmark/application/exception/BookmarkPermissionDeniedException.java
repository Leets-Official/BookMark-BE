package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkPermissionDeniedException extends BusinessException {
    public BookmarkPermissionDeniedException() {
        super(BookmarkErrorCode.NO_BOOKMARK_PERMISSION_EXCEPTION);
    }
}

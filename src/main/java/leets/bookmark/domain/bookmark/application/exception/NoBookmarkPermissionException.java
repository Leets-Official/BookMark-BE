package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class NoBookmarkPermissionException extends BusinessException {
    public NoBookmarkPermissionException() {
        super(BookmarkErrorCode.NO_BOOKMARK_PERMISSION_EXCEPTION);
    }
}

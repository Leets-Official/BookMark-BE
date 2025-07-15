package leets.bookmark.domain.bookmark.application.exeception;

import leets.bookmark.global.common.exception.BusinessException;
import leets.bookmark.global.common.exception.ErrorCode;

public class BookMarkNotFoundException extends BusinessException {
    public BookMarkNotFoundException() {
        super(ErrorCode.BOOKMARK_NOT_FOUND.getStatusCode(), ErrorCode.BOOKMARK_NOT_FOUND.getMessage());
    }
}

package leets.bookmark.domain.bookmark.application.exeception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookMarkNotFoundException extends BusinessException {
    public BookMarkNotFoundException() {
        super(BookmarkErrorCode.NOT_FOUND);
    }
}


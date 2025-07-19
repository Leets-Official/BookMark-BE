package leets.bookmark.domain.exception;

import leets.bookmark.global.common.exception.BusinessException;
import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BookmarkNotFoundException extends BusinessException {

    public BookmarkNotFoundException() {
        super(ErrorCode.BOOKMARK_NOT_FOUND);
    }
}

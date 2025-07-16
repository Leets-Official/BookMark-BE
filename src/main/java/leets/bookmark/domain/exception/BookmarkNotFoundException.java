package leets.bookmark.domain.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BookmarkNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookmarkNotFoundException() {
        super(ErrorCode.BOOKMARK_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.BOOKMARK_NOT_FOUND;
    }
}

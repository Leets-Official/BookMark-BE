package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkUpdateFieldEmptyException extends BusinessException {
  public BookmarkUpdateFieldEmptyException() {
    super(BookmarkErrorCode.BOOKMARK_UPDATE_FIELD_EMPTY_EXCEPTION);
  }
}

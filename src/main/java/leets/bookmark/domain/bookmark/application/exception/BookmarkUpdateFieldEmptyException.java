package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class BookmarkUpdateFieldEmptyException extends BusinessException {
  public BookmarkUpdateFieldEmptyException() {
    super(BookmarkErrorCode.Bookmark_Update_Field_Empty_Exception);
  }
}

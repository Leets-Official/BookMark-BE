package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagCategoryMismatchException extends BusinessException {
    public TagCategoryMismatchException(){
        super(BookmarkErrorCode.TAG_CATEGORY_MISMATCH_EXCEPTION);
    }
}

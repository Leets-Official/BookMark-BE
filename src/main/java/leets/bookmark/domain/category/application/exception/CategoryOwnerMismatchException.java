package leets.bookmark.domain.category.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class CategoryOwnerMismatchException extends BusinessException {
    public CategoryOwnerMismatchException() {
        super(CategoryErrorCode.CATEGORY_OWNER_MISMATCH_EXCEPTION);
    }
}

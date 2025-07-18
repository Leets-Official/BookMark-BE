package leets.bookmark.domain.category.application.exception;


import leets.bookmark.global.common.exception.BusinessException;

public class DuplicatedCategoryNameException extends BusinessException {
    public DuplicatedCategoryNameException() {
        super(CategoryErrorCode.DUPLICATE_NAME);
    }
}

package leets.bookmark.global.exception;


import leets.bookmark.global.common.entity.CategoryErrorCode;
import leets.bookmark.global.common.exception.BusinessException;

public class DuplicatedCategoryNameException extends BusinessException {
    public DuplicatedCategoryNameException(String name) {
        super(CategoryErrorCode.DUPLICATE_NAME);
    }
}

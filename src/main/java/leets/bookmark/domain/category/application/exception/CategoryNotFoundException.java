package leets.bookmark.domain.category.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException() {
        super(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }
}

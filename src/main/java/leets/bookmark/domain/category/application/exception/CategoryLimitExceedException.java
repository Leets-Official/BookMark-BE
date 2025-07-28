package leets.bookmark.domain.category.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class CategoryLimitExceedException extends BusinessException {
    public CategoryLimitExceedException() {
        super(CategoryErrorCode.CATEGORY_LIMIT_EXCEED_EXCEPTION);
    }
}

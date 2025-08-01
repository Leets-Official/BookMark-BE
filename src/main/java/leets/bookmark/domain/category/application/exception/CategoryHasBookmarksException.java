package leets.bookmark.domain.category.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class CategoryHasBookmarksException extends BusinessException {
    public CategoryHasBookmarksException() {
        super(CategoryErrorCode.CATEGORY_HAS_BOOKMARK_EXCEPTION);
    }
}

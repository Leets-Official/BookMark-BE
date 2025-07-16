package leets.bookmark.global.exception;


import leets.bookmark.global.common.entity.CategoryErrorCode;

public class DuplicatedCategoryNameException extends RuntimeException {
    public DuplicatedCategoryNameException(String name) {
        super(CategoryErrorCode.DUPLICATE_NAME.getMessage() + ": " + name);
    }
}

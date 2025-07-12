package leets.bookmark.global.exception;


import leets.bookmark.global.common.entity.CategoryErrorCode;

public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String name) {
        super(CategoryErrorCode.DUPLICATE_NAME.getMessage() + ": " + name);
    }
}

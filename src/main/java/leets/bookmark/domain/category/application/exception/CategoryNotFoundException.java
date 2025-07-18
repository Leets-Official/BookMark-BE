package leets.bookmark.domain.category.application.exception;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    private final CategoryErrorCode errorCode;

    public CategoryNotFoundException() {
        super(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage());
        this.errorCode = CategoryErrorCode.CATEGORY_NOT_FOUND;
    }
}
